package cl.skyscraper.clients.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.skyscraper.clients.dto.LoginRequestDTO;
import cl.skyscraper.clients.dto.RegisterRequestDTO;
import cl.skyscraper.clients.dto.TokenResponseDTO;
import cl.skyscraper.clients.model.Token;
import cl.skyscraper.clients.model.User;
import cl.skyscraper.clients.repository.TokenRepository;
import cl.skyscraper.clients.repository.UserRepository;
import cl.skyscraper.clients.util.Messages;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public TokenResponseDTO register(RegisterRequestDTO request){
        var user = User.builder()
                    .run(request.run())
                    .dv(request.dv())
                    .username(request.username())
                    .name(request.name())
                    .middlename(request.middlename())
                    .lastname(request.lastname())
                    .secondlastname(request.secondlastname())
                    .birthdate(request.birthdate())
                    .phonenumber(request.phonenumber())
                    .email(request.email())
                    .address(request.address())
                    .password(passwordEncoder.encode(request.password()))
                    .build();
        var savedUser = userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        var jwtRefreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(savedUser, jwtToken);
        return new TokenResponseDTO(jwtToken, jwtRefreshToken);

    }

    public TokenResponseDTO login(LoginRequestDTO request){
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.email(), 
                request.password()
            )
        );

        var user = userRepository.findByEmail(request.email())
            .orElseThrow();
        
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user); 
        saveUserToken(user, jwtToken);
        return new TokenResponseDTO(jwtToken, refreshToken);
    }

    private void saveUserToken(User user, String jwtToken){
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(final User user) {
        final List<Token> validUserTokens = tokenRepository
            .findAllValidIsFalseOrRevokedIsFalseByUserId(user.getId());
        
            if(!validUserTokens.isEmpty()) {
                for (final Token token : validUserTokens) {
                    token.setExpired(true);
                    token.setRevoked(true);
                }
                tokenRepository.saveAll(validUserTokens);
            }
    }

    public TokenResponseDTO refreshToken(final String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException(Messages.INVALID_BEARER_TOKEN);
        }

        final String refreshToken =authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if (userEmail == null) {
            throw new IllegalArgumentException(Messages.INVALID_REFRESH_TOKEN);
        }

        final User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException(userEmail));

        if (!jwtService.isTokenValid(refreshToken, user)){
            throw new IllegalArgumentException(Messages.INVALID_REFRESH_TOKEN);
        }

        final String accessToken = jwtService.generateToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponseDTO(accessToken, refreshToken);
    }
}

package cl.skyscraper.clients.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;

import cl.skyscraper.clients.repository.TokenRepository;
import cl.skyscraper.clients.model.Token;
import cl.skyscraper.clients.util.Messages;
import lombok.RequiredArgsConstructor;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final TokenRepository tokenRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable) // Deshabilitamos CSRF porque usaremos JWT
            .authorizeHttpRequests(req -> 
            req 
                .requestMatchers("/api_clients/v1/auth/**")
                    .permitAll()
                .requestMatchers(HttpMethod.GET,"/api_clients/v1/reviews/**") 
                    .permitAll()
                .requestMatchers(HttpMethod.GET, "/api_clients/v1/users/me")
                    .authenticated()
                .requestMatchers(HttpMethod.GET,"/api_clients/v1/users/**") 
                    .permitAll()
                .requestMatchers("/api_clients/v1/passengers/**") 
                    .permitAll()
                .anyRequest().authenticated() // Todo lo demás requiere token
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
            .logout(logout ->
                    logout.logoutUrl("/api_clients/v1/auth/logout")
                    .addLogoutHandler((request, response, authentication) -> {
                        final var authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
                        logout(authHeader);
                    })
                    .logoutSuccessHandler((request, response, authentication) -> 
                            SecurityContextHolder.clearContext())
            );
        
        return http.build();
    }

    private void logout(final String token) {
        if(token == null || !token.startsWith("Bearer ")) {
            throw new IllegalArgumentException(Messages.INVALID_BEARER_TOKEN);
        }

        final String jwtToken = token.substring(7);
        final Token foundToken = tokenRepository.findByToken(jwtToken)
                    .orElseThrow(() -> new IllegalArgumentException(Messages.INVALID_BEARER_TOKEN));
        foundToken.setExpired(true);
        foundToken.setRevoked(true);
        tokenRepository.save(foundToken);
    }
}
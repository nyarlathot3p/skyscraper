package cl.skyscraper.clients.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.skyscraper.clients.dto.LoginRequestDTO;
import cl.skyscraper.clients.dto.RegisterRequestDTO;
import cl.skyscraper.clients.dto.TokenResponseDTO;
import cl.skyscraper.clients.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/api_clients/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;
    // ok
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@Valid @RequestBody final RegisterRequestDTO request){
        final TokenResponseDTO token = service.register(request);
        return ResponseEntity.ok(token);
    }
    // ok
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody final LoginRequestDTO request){
        final TokenResponseDTO token = service.login(request);
        return ResponseEntity.ok(token);
    }
    // ok
    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken (@RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return service.refreshToken(authHeader);
    } 
    
}

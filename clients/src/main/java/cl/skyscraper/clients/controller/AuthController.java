package cl.skyscraper.clients.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Autenticación", description = "Endpoints para registro de usuario, inicio de sesión y refresco de tokens")
public class AuthController {

    private final AuthService service;
    // ok
    @Operation(summary = "Registrar un nuevo usuario", description = "Crea una nueva cuenta y devuelve tokens de autenticación")
    @ApiResponse(responseCode = "200", description = "Usuario registrado correctamente")
    @ApiResponse(responseCode = "400", description = "Payload de registro inválido")
    @PostMapping("/register")
    public ResponseEntity<TokenResponseDTO> register(@Valid @org.springframework.web.bind.annotation.RequestBody final RegisterRequestDTO request){
        final TokenResponseDTO token = service.register(request);
        return ResponseEntity.ok(token);
    }
    // ok
    @Operation(summary = "Autenticar a un usuario", description = "Valida credenciales y devuelve tokens de autenticación")
    @ApiResponse(responseCode = "200", description = "Usuario autenticado correctamente")
    @ApiResponse(responseCode = "401", description = "Credenciales inválidas")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @org.springframework.web.bind.annotation.RequestBody final LoginRequestDTO request){
        final TokenResponseDTO token = service.login(request);
        return ResponseEntity.ok(token);
    }
    // ok
    @Operation(summary = "Refrescar un token de acceso", description = "Usa el encabezado de autorización provisto para emitir un nuevo token de acceso")
    @ApiResponse(responseCode = "200", description = "Token actualizado correctamente")
    @ApiResponse(responseCode = "401", description = "Token inválido o inautorizado")
    @PostMapping("/refresh")
    public TokenResponseDTO refreshToken (@Parameter(description = "Token Bearer usado para actualizar la sesión", required = true, example = "Bearer <token>") @RequestHeader(HttpHeaders.AUTHORIZATION) final String authHeader) {
        return service.refreshToken(authHeader);
    } 
    
}

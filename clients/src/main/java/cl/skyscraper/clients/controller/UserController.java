package cl.skyscraper.clients.controller;

import cl.skyscraper.clients.dto.ChangePasswordRequestDTO;
import cl.skyscraper.clients.dto.MessageResponseDTO;
import cl.skyscraper.clients.dto.UserNoTokenDTO;
import cl.skyscraper.clients.dto.UserResponseDTO;
import cl.skyscraper.clients.service.UserService;
import cl.skyscraper.clients.util.Messages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api_clients/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para el perfil y gestión de cuentas de usuario")
public class UserController {

    private final UserService userService;

    @Operation(summary = "Listar todos los usuarios", description = "Devuelve la lista completa de usuarios")
    @ApiResponse(responseCode = "200", description = "Usuarios obtenidos correctamente")
    @GetMapping()
    public ResponseEntity<List<UserNoTokenDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
    

    @Operation(summary = "Obtener un usuario por id", description = "Devuelve el usuario que coincide con el identificador proporcionado")
    @ApiResponse(responseCode = "200", description = "Usuario encontrado")
    @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@Parameter(description = "User id", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    
    // ok
    @Operation(summary = "Obtener usuario actual", description = "Devuelve el perfil del usuario autenticado")
    @ApiResponse(responseCode = "200", description = "Usuario actual obtenido correctamente")
    @GetMapping("/me")
    public ResponseEntity<UserNoTokenDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    // ok
    @Operation(summary = "Actualizar el usuario actual", description = "Actualiza el perfil del usuario autenticado")
    @ApiResponse(responseCode = "200", description = "Usuario actualizado correctamente")
    @ApiResponse(responseCode = "400", description = "Payload de usuario inválido")
    @PutMapping
    public ResponseEntity<UserNoTokenDTO> updateUser(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del perfil de usuario a actualizar", required = true) @org.springframework.web.bind.annotation.RequestBody UserNoTokenDTO updateData) {
        UserNoTokenDTO updated = userService.updateUser(updateData);
        return ResponseEntity.ok(updated);
    }

    // ok
    @Operation(summary = "Eliminar el usuario actual", description = "Elimina la cuenta del usuario autenticado")
    @ApiResponse(responseCode = "204", description = "Usuario eliminado correctamente")
    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();   
        return ResponseEntity.noContent().build();
    }

    // ok
    @Operation(summary = "Cambiar contraseña", description = "Actualiza la contraseña del usuario autenticado")
    @ApiResponse(responseCode = "200", description = "Contraseña cambiada correctamente")
    @ApiResponse(responseCode = "400", description = "Payload de contraseña inválido")
    @PutMapping("/change_password")
    public ResponseEntity<MessageResponseDTO> changePassword(@Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Valores de contraseña actual y nueva", required = true) @org.springframework.web.bind.annotation.RequestBody ChangePasswordRequestDTO request) {
        userService.changePassword(request.currentPassword(), request.newPassword());
        return ResponseEntity.ok(MessageResponseDTO.builder()
                .message(Messages.PASSWORD_CHANGED_SUCCESSFULLY)
                .build());
    }
}

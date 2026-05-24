package cl.skyscraper.clients.controller;

import cl.skyscraper.clients.dto.ChangePasswordRequestDTO;
import cl.skyscraper.clients.dto.MessageResponseDTO;
import cl.skyscraper.clients.dto.UserNoTokenDTO;
import cl.skyscraper.clients.dto.UserResponseDTO;
import cl.skyscraper.clients.service.UserService;
import cl.skyscraper.clients.util.Messages;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api_clients/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping()
    public ResponseEntity<List<UserNoTokenDTO>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> findById(@RequestParam Long id) {
        return ResponseEntity.ok(userService.findById(id));
    }
    
    // ok
    @GetMapping("/me")
    public ResponseEntity<UserNoTokenDTO> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    // ok
    @PutMapping
    public ResponseEntity<UserNoTokenDTO> updateUser(@Valid @RequestBody UserNoTokenDTO updateData) {
        UserNoTokenDTO updated = userService.updateUser(updateData);
        return ResponseEntity.ok(updated);
    }

    // ok
    @DeleteMapping
    public ResponseEntity<Void> deleteUser() {
        userService.deleteUser();   
        return ResponseEntity.noContent().build();
    }

    // ok
    @PutMapping("/change_password")
    public ResponseEntity<MessageResponseDTO> changePassword(@Valid @RequestBody ChangePasswordRequestDTO request) {
        userService.changePassword(request.currentPassword(), request.newPassword());
        return ResponseEntity.ok(MessageResponseDTO.builder()
                .message(Messages.PASSWORD_CHANGED_SUCCESSFULLY)
                .build());
    }
}

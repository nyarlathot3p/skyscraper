package cl.skyscraper.clients.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.skyscraper.clients.dto.UserNoTokenDTO;
import cl.skyscraper.clients.dto.UserResponseDTO;
import cl.skyscraper.clients.model.User;
import cl.skyscraper.clients.repository.UserRepository;
import cl.skyscraper.clients.util.Messages;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User getAuthenticatedUser() {
        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException(Messages.USER_NOT_FOUND_IN_SESSION));
    }

    public UserResponseDTO findById(Long id){
        User user = userRepository.findById(id)
         .orElseThrow(() -> new RuntimeException("User no encontrado"));
        return mapToUserDTO(user);
    }

    public List<UserNoTokenDTO> findAll(){
        return userRepository.findAll()
                .stream()
                .map(this::mapToNoTokenDTO)
                .toList();
    }

    public UserNoTokenDTO getCurrentUser() {
        User user = getAuthenticatedUser();
        UserNoTokenDTO noDtoUser = mapToNoTokenDTO(user);
        return noDtoUser;
    }

    public UserNoTokenDTO updateUser(UserNoTokenDTO updateData) {
        User currentUser = getAuthenticatedUser();

        if (updateData.getRun() != null && !updateData.getRun().equals(currentUser.getRun())) {
            if (userRepository.findByRun(updateData.getRun()).isPresent()) {
                throw new RuntimeException("El RUN ingresado ya está registrado en otra cuenta.");
            }
            currentUser.setRun(updateData.getRun());
        }

        if (updateData.getUsername() != null && !updateData.getUsername().equals(currentUser.getUsername())) {
            if (userRepository.findByUsername(updateData.getUsername()).isPresent()) {
                throw new RuntimeException("El nombre de usuario ya está en uso.");
            }
            currentUser.setUsername(updateData.getUsername());
        }

        if (updateData.getEmail() != null && !updateData.getEmail().equals(currentUser.getEmail())) {
            if (userRepository.findByEmail(updateData.getEmail()).isPresent()) {
                throw new RuntimeException("El correo ingresado ya está registrado por otro usuario.");
            }
            currentUser.setEmail(updateData.getEmail());
        }
        // Update all fields except password
        if (updateData.getDv() != null) {
            currentUser.setDv(updateData.getDv());
        }
        if (updateData.getName() != null) {
            currentUser.setName(updateData.getName());
        }
        if (updateData.getMiddlename() != null) {
            currentUser.setMiddlename(updateData.getMiddlename());
        }
        if (updateData.getLastname() != null) {
            currentUser.setLastname(updateData.getLastname());
        }
        if (updateData.getSecondlastname() != null) {
            currentUser.setSecondlastname(updateData.getSecondlastname());
        }
        if (updateData.getBirthdate() != null) {
            currentUser.setBirthdate(updateData.getBirthdate());
        }
        if (updateData.getPhonenumber() != null) {
            currentUser.setPhonenumber(updateData.getPhonenumber());
        }
        if (updateData.getAddress() != null) {
            currentUser.setAddress(updateData.getAddress());
        }
        userRepository.save(currentUser);
        UserNoTokenDTO noDtoUser = mapToNoTokenDTO(currentUser);
        return noDtoUser;
    }

    public void deleteUser() {
        User currentUser = getAuthenticatedUser();
        userRepository.delete(currentUser);
    }

    public void changePassword(String currentPassword, String newPassword) {
        User currentUser = getAuthenticatedUser();
        
        if (!passwordEncoder.matches(currentPassword, currentUser.getPassword())) {
            throw new RuntimeException(Messages.CURRENT_PASSWORD_INCORRECT);
        }
        
        currentUser.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(currentUser);
    }

    public UserResponseDTO mapToUserDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setRun(user.getRun());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public UserNoTokenDTO mapToNoTokenDTO(User user) {
        UserNoTokenDTO dto = new UserNoTokenDTO();
        dto.setId(user.getId());
        dto.setRun(user.getRun());
        dto.setDv(user.getDv());
        dto.setUsername(user.getUsername());
        dto.setName(user.getName());
        dto.setMiddlename(user.getMiddlename());
        dto.setLastname(user.getLastname());
        dto.setSecondlastname(user.getSecondlastname());
        dto.setEmail(user.getEmail());
        dto.setBirthdate(user.getBirthdate());
        dto.setPhonenumber(user.getPhonenumber());
        dto.setAddress(user.getAddress());
        return dto;
}
}

package cl.skyscraper.clients.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import cl.skyscraper.clients.dto.UserNoTokenDTO;
import cl.skyscraper.clients.dto.UserResponseDTO;
import cl.skyscraper.clients.model.User;
import cl.skyscraper.clients.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        SecurityContextHolder.setContext(securityContext);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);
        lenient().when(authentication.getName()).thenReturn("user@example.com");
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void findById_shouldReturnUserResponseDTO_whenUserExists() {
        User user = buildUser(1L, "user@example.com");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.findById(1L);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(user.getRun(), response.getRun());
        assertEquals(user.getEmail(), response.getEmail());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        User user = buildUser(1L, "user@example.com");
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<UserNoTokenDTO> users = userService.findAll();

        assertEquals(1, users.size());
        assertEquals(user.getId(), users.get(0).getId());
    }

    @Test
    void getCurrentUser_shouldReturnUserNoTokenDTO() {
        User user = buildUser(1L, "user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        UserNoTokenDTO result = userService.getCurrentUser();

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getUsername(), result.getUsername());
    }

    @Test
    void updateUser_shouldSaveChangedFields() {
        User currentUser = buildUser(1L, "user@example.com");
        UserNoTokenDTO updateData = new UserNoTokenDTO();
        updateData.setRun(7777777);
        updateData.setUsername("new-username");
        updateData.setEmail("new@example.com");
        updateData.setDv("9");
        updateData.setName("NewName");
        updateData.setMiddlename("Middle");
        updateData.setLastname("Last");
        updateData.setSecondlastname("Second");
        updateData.setBirthdate(LocalDate.of(1985, 1, 1));
        updateData.setPhonenumber("+123456789");
        updateData.setAddress("New address");

        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(currentUser));
        when(userRepository.findByRun(7777777)).thenReturn(Optional.empty());
        when(userRepository.findByUsername("new-username")).thenReturn(Optional.empty());
        when(userRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserNoTokenDTO result = userService.updateUser(updateData);

        assertEquals(7777777, result.getRun());
        assertEquals("new-username", result.getUsername());
        assertEquals("new@example.com", result.getEmail());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void deleteUser_shouldDeleteAuthenticatedUser() {
        User user = buildUser(1L, "user@example.com");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));

        userService.deleteUser();

        verify(userRepository).delete(user);
    }

    @Test
    void changePassword_shouldUpdatePassword_whenCurrentPasswordMatches() {
        User user = buildUser(1L, "user@example.com");
        user.setPassword("encoded-old");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("current", "encoded-old")).thenReturn(true);
        when(passwordEncoder.encode("new-password")).thenReturn("encoded-new");

        userService.changePassword("current", "new-password");

        assertEquals("encoded-new", user.getPassword());
        verify(userRepository).save(user);
    }

    @Test
    void changePassword_shouldThrowException_whenCurrentPasswordDoesNotMatch() {
        User user = buildUser(1L, "user@example.com");
        user.setPassword("encoded-old");
        when(userRepository.findByEmail("user@example.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrong", "encoded-old")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> userService.changePassword("wrong", "new-password"));

        assertTrue(exception.getMessage().contains("incorrecto") || exception.getMessage().contains("incorrect"));
        verify(userRepository, never()).save(any(User.class));
    }

    private User buildUser(Long id, String email) {
        return User.builder()
                .id(id)
                .run(11111111)
                .dv("1")
                .username("user1")
                .name("User")
                .middlename("Test")
                .lastname("Lastname")
                .secondlastname("Second")
                .birthdate(LocalDate.of(1990, 1, 1))
                .phonenumber("+5551234")
                .email(email)
                .address("Address")
                .password("password")
                .build();
    }
}

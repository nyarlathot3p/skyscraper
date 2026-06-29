package cl.skyscraper.clients.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import cl.skyscraper.clients.dto.ChangePasswordRequestDTO;
import cl.skyscraper.clients.dto.UserNoTokenDTO;
import cl.skyscraper.clients.dto.UserResponseDTO;
import cl.skyscraper.clients.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void shouldReturnAllUsers() throws Exception {
        UserNoTokenDTO user = new UserNoTokenDTO();
        user.setId(1L);
        user.setRun(12345678);
        user.setDv("9");
        user.setUsername("usuario12345678");
        user.setName("Juan");
        user.setMiddlename("Carlos");
        user.setLastname("Gómez");
        user.setSecondlastname("Martínez");
        user.setEmail("usuario12345678@ejemplo.cl");
        user.setBirthdate(LocalDate.of(1995, 7, 15));
        user.setPhonenumber("+56912345678");
        user.setAddress("Av. Libertador 1234");

        given(userService.findAll()).willReturn(List.of(user));

        mockMvc.perform(get("/api_clients/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].email").value("usuario12345678@ejemplo.cl"));
    }

    @Test
    void shouldReturnUserById() throws Exception {
        UserResponseDTO response = new UserResponseDTO();
        response.setId(1L);
        response.setRun(12345678);
        response.setName("Juan");
        response.setEmail("usuario12345678@ejemplo.cl");

        given(userService.findById(1L)).willReturn(response);

        mockMvc.perform(get("/api_clients/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("usuario12345678@ejemplo.cl"));
    }

    @Test
    void shouldReturnCurrentUser() throws Exception {
        UserNoTokenDTO response = new UserNoTokenDTO();
        response.setId(1L);
        response.setRun(12345678);
        response.setDv("9");
        response.setUsername("usuario12345678");
        response.setName("Juan");
        response.setMiddlename("Carlos");
        response.setLastname("Gómez");
        response.setSecondlastname("Martínez");
        response.setEmail("usuario12345678@ejemplo.cl");
        response.setBirthdate(LocalDate.of(1995, 7, 15));
        response.setPhonenumber("+56912345678");
        response.setAddress("Av. Libertador 1234");

        given(userService.getCurrentUser()).willReturn(response);

        mockMvc.perform(get("/api_clients/v1/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("usuario12345678@ejemplo.cl"));
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UserNoTokenDTO request = new UserNoTokenDTO();
        request.setId(1L);
        request.setRun(12345678);
        request.setDv("9");
        request.setUsername("usuario12345678");
        request.setName("Juan");
        request.setMiddlename("Carlos");
        request.setLastname("Gómez");
        request.setSecondlastname("Martínez");
        request.setEmail("usuario12345678@ejemplo.cl");
        request.setBirthdate(LocalDate.of(1995, 7, 15));
        request.setPhonenumber("+56912345678");
        request.setAddress("Av. Libertador 1234");

        given(userService.updateUser(any(UserNoTokenDTO.class))).willReturn(request);

        mockMvc.perform(put("/api_clients/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("usuario12345678@ejemplo.cl"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser();

        mockMvc.perform(delete("/api_clients/v1/users"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldChangePassword() throws Exception {
        ChangePasswordRequestDTO request = new ChangePasswordRequestDTO("oldPass123", "newPass123");
        doNothing().when(userService).changePassword(any(String.class), any(String.class));

        mockMvc.perform(put("/api_clients/v1/users/change_password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Contraseña cambió exitosamente"));
    }
}

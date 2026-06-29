package cl.skyscraper.clients.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

import cl.skyscraper.clients.model.Passenger;
import cl.skyscraper.clients.service.PassengerService;

@ExtendWith(MockitoExtension.class)
class PassengerControllerTest {

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Mock
    private PassengerService passengerService;

    @InjectMocks
    private PassengerController passengerController;

    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mockMvc = MockMvcBuilders.standaloneSetup(passengerController).build();
    }

    @Test
    void shouldReturnAllPassengers() throws Exception {
        Passenger passenger = Passenger.builder()
                .id(1L)
                .run(12345678)
                .dv("9")
                .firstName("Ana")
                .secondName("María")
                .paternalLastName("Pérez")
                .maternalLastName("González")
                .birthDate(LocalDate.of(1990, 5, 20))
                .phone("+56912345678")
                .email("pasajero12345678@ejemplo.cl")
                .build();

        given(passengerService.findAll()).willReturn(List.of(passenger));

        mockMvc.perform(get("/api_clients/v1/passengers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].run").value(12345678))
                .andExpect(jsonPath("$[0].email").value("pasajero12345678@ejemplo.cl"));
    }

    @Test
    void shouldReturnPassengerById() throws Exception {
        Passenger passenger = Passenger.builder()
                .id(1L)
                .run(12345678)
                .dv("9")
                .firstName("Ana")
                .secondName("María")
                .paternalLastName("Pérez")
                .maternalLastName("González")
                .birthDate(LocalDate.of(1990, 5, 20))
                .phone("+56912345678")
                .email("pasajero12345678@ejemplo.cl")
                .build();

        given(passengerService.findById(1L)).willReturn(passenger);

        mockMvc.perform(get("/api_clients/v1/passengers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.run").value(12345678))
                .andExpect(jsonPath("$.email").value("pasajero12345678@ejemplo.cl"));
    }

    @Test
    void shouldCreatePassenger() throws Exception {
        Passenger request = Passenger.builder()
                .run(12345679)
                .dv("5")
                .firstName("Luis")
                .secondName("Miguel")
                .paternalLastName("Torres")
                .maternalLastName("López")
                .birthDate(LocalDate.of(1985, 3, 10))
                .phone("+56987654321")
                .email("pasajero12345679@ejemplo.cl")
                .build();

        Passenger saved = Passenger.builder()
                .id(2L)
                .run(request.getRun())
                .dv(request.getDv())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .paternalLastName(request.getPaternalLastName())
                .maternalLastName(request.getMaternalLastName())
                .birthDate(request.getBirthDate())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();

        given(passengerService.createPassenger(any(Passenger.class))).willReturn(saved);

        mockMvc.perform(post("/api_clients/v1/passengers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.email").value("pasajero12345679@ejemplo.cl"));
    }

    @Test
    void shouldUpdatePassenger() throws Exception {
        Passenger request = Passenger.builder()
                .run(12345678)
                .dv("9")
                .firstName("Ana")
                .secondName("María")
                .paternalLastName("Pérez")
                .maternalLastName("González")
                .birthDate(LocalDate.of(1990, 5, 20))
                .phone("+56912345678")
                .email("pasajero12345678@ejemplo.cl")
                .build();

        Passenger updated = Passenger.builder()
                .id(1L)
                .run(request.getRun())
                .dv(request.getDv())
                .firstName(request.getFirstName())
                .secondName(request.getSecondName())
                .paternalLastName(request.getPaternalLastName())
                .maternalLastName(request.getMaternalLastName())
                .birthDate(request.getBirthDate())
                .phone(request.getPhone())
                .email(request.getEmail())
                .build();

        given(passengerService.updatePassenger(eq(1L), any())).willReturn(updated);

        mockMvc.perform(put("/api_clients/v1/passengers/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("pasajero12345678@ejemplo.cl"));
    }

    @Test
    void shouldDeletePassenger() throws Exception {
        doNothing().when(passengerService).deletePassenger(1L);

        mockMvc.perform(delete("/api_clients/v1/passengers/1"))
                .andExpect(status().isNoContent());
    }
}

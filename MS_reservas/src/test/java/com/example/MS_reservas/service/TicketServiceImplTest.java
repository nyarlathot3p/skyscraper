package com.example.MS_reservas.service;

import com.example.MS_reservas.client.ClienteClient;
import com.example.MS_reservas.client.FlotaClient;
import com.example.MS_reservas.client.PagoClient;
import com.example.MS_reservas.client.VueloClient;
import com.example.MS_reservas.exception.ResourceNotFoundException;
import com.example.MS_reservas.model.dto.ReservaRequestDTO;
import com.example.MS_reservas.model.dto.TicketDTO;
import com.example.MS_reservas.model.dto.external.AsientoDTO;
import com.example.MS_reservas.model.dto.external.ClienteDTO;
import com.example.MS_reservas.model.dto.external.PagoRequestDTO;
import com.example.MS_reservas.model.dto.external.PagoResponseDTO;
import com.example.MS_reservas.model.dto.external.VueloDTO;
import com.example.MS_reservas.model.entity.Ticket;
import com.example.MS_reservas.repository.TicketRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceImplTest {

    @Mock
    private TicketRepository repository;

    @Mock
    private VueloClient vueloClient;

    @Mock
    private ClienteClient clienteClient;

    @Mock
    private FlotaClient flotaClient;

    @Mock
    private PagoClient pagoClient;

    @InjectMocks
    private TicketServiceImpl service;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testGetAll() {
        // Arrange
        Ticket t1 = new Ticket(1L, faker.number().randomNumber(), faker.number().randomNumber(), faker.number().randomNumber(), faker.number().randomNumber());
        Ticket t2 = new Ticket(2L, faker.number().randomNumber(), faker.number().randomNumber(), faker.number().randomNumber(), faker.number().randomNumber());
        when(repository.findAll()).thenReturn(Arrays.asList(t1, t2));

        // Act
        List<TicketDTO> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(t1.getId(), result.get(0).getId());
        assertEquals(t2.getId(), result.get(1).getId());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetById_Success() {
        // Arrange
        Long id = 1L;
        Ticket ticket = new Ticket(id, faker.number().randomNumber(), faker.number().randomNumber(), faker.number().randomNumber(), faker.number().randomNumber());
        when(repository.findById(id)).thenReturn(Optional.of(ticket));

        // Act
        TicketDTO result = service.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals(ticket.getIdComprador(), result.getIdComprador());
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testGetById_NotFound() {
        // Arrange
        Long id = 99L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.getById(id));
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void testSave() {
        // Arrange
        TicketDTO dto = new TicketDTO(null, 10L, 20L, 30L, 40L);
        Ticket savedEntity = new Ticket(1L, 10L, 20L, 30L, 40L);
        when(repository.save(any(Ticket.class))).thenReturn(savedEntity);

        // Act
        TicketDTO result = service.save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(dto.getIdComprador(), result.getIdComprador());
        verify(repository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testDelete_Success() {
        // Arrange
        Long id = 1L;
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        // Act
        service.delete(id);

        // Assert
        verify(repository, times(1)).existsById(id);
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void testDelete_NotFound() {
        // Arrange
        Long id = 99L;
        when(repository.existsById(id)).thenReturn(false);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));
        verify(repository, times(1)).existsById(id);
        verify(repository, never()).deleteById(id);
    }

    @Test
    public void testComprarTicket_Success() {
        // Arrange
        Long idComprador = faker.number().numberBetween(1L, 100L);
        Long idPasajero = faker.number().numberBetween(1L, 100L);
        Long idAsientoVuelo = faker.number().numberBetween(1L, 100L);
        Long idVuelo = faker.number().numberBetween(1L, 100L);
        Double valor = faker.number().randomDouble(2, 50, 500);
        Long idMedioPago = faker.number().numberBetween(1L, 5L);
        Long idTransaccion = faker.number().randomNumber();

        ReservaRequestDTO request = new ReservaRequestDTO(idComprador, idPasajero, idAsientoVuelo, idVuelo, valor, idMedioPago);

        ClienteDTO mockCliente = new ClienteDTO(); // Lombok setters or mock fields
        VueloDTO mockVuelo = new VueloDTO();
        AsientoDTO mockAsiento = new AsientoDTO();
        PagoResponseDTO mockPago = new PagoResponseDTO(idTransaccion, "APROBADO", "2026-06-29");

        when(clienteClient.getClienteById(idComprador)).thenReturn(mockCliente);
        when(vueloClient.getVueloById(idVuelo)).thenReturn(mockVuelo);
        when(flotaClient.getAsientoById(idAsientoVuelo)).thenReturn(mockAsiento);
        when(pagoClient.procesarPago(any(PagoRequestDTO.class))).thenReturn(mockPago);

        Ticket savedTicket = new Ticket(1L, idComprador, idPasajero, idAsientoVuelo, idTransaccion);
        when(repository.save(any(Ticket.class))).thenReturn(savedTicket);

        // Act
        TicketDTO result = service.comprarTicket(request);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(idTransaccion, result.getIdTransaccion());
        verify(clienteClient, times(1)).getClienteById(idComprador);
        verify(vueloClient, times(1)).getVueloById(idVuelo);
        verify(flotaClient, times(1)).getAsientoById(idAsientoVuelo);
        verify(pagoClient, times(1)).procesarPago(any(PagoRequestDTO.class));
        verify(repository, times(1)).save(any(Ticket.class));
    }

    @Test
    public void testComprarTicket_ClienteNotFound() {
        // Arrange
        ReservaRequestDTO request = new ReservaRequestDTO(1L, 2L, 3L, 4L, 150.0, 1L);
        when(clienteClient.getClienteById(1L)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.comprarTicket(request));
        verify(clienteClient, times(1)).getClienteById(1L);
        verify(vueloClient, never()).getVueloById(anyLong());
    }

    @Test
    public void testComprarTicket_VueloNotFound() {
        // Arrange
        ReservaRequestDTO request = new ReservaRequestDTO(1L, 2L, 3L, 4L, 150.0, 1L);
        when(clienteClient.getClienteById(1L)).thenReturn(new ClienteDTO());
        when(vueloClient.getVueloById(4L)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.comprarTicket(request));
        verify(clienteClient, times(1)).getClienteById(1L);
        verify(vueloClient, times(1)).getVueloById(4L);
        verify(flotaClient, never()).getAsientoById(anyLong());
    }

    @Test
    public void testComprarTicket_AsientoNotFound() {
        // Arrange
        ReservaRequestDTO request = new ReservaRequestDTO(1L, 2L, 3L, 4L, 150.0, 1L);
        when(clienteClient.getClienteById(1L)).thenReturn(new ClienteDTO());
        when(vueloClient.getVueloById(4L)).thenReturn(new VueloDTO());
        when(flotaClient.getAsientoById(3L)).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.comprarTicket(request));
        verify(clienteClient, times(1)).getClienteById(1L);
        verify(vueloClient, times(1)).getVueloById(4L);
        verify(flotaClient, times(1)).getAsientoById(3L);
        verify(pagoClient, never()).procesarPago(any(PagoRequestDTO.class));
    }

    @Test
    public void testComprarTicket_PagoFailed() {
        // Arrange
        ReservaRequestDTO request = new ReservaRequestDTO(1L, 2L, 3L, 4L, 150.0, 1L);
        when(clienteClient.getClienteById(1L)).thenReturn(new ClienteDTO());
        when(vueloClient.getVueloById(4L)).thenReturn(new VueloDTO());
        when(flotaClient.getAsientoById(3L)).thenReturn(new AsientoDTO());
        when(pagoClient.procesarPago(any(PagoRequestDTO.class))).thenThrow(new RuntimeException("Saldo insuficiente"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> service.comprarTicket(request));
        verify(clienteClient, times(1)).getClienteById(1L);
        verify(vueloClient, times(1)).getVueloById(4L);
        verify(flotaClient, times(1)).getAsientoById(3L);
        verify(pagoClient, times(1)).procesarPago(any(PagoRequestDTO.class));
        verify(repository, never()).save(any(Ticket.class));
    }
}

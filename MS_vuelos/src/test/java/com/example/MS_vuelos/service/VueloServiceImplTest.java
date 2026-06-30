package com.example.MS_vuelos.service;

import com.example.MS_vuelos.exception.ResourceNotFoundException;
import com.example.MS_vuelos.model.dto.VueloDTO;
import com.example.MS_vuelos.model.entity.EstadoVuelo;
import com.example.MS_vuelos.model.entity.Vuelo;
import com.example.MS_vuelos.repository.EstadoVueloRepository;
import com.example.MS_vuelos.repository.VueloRepository;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class VueloServiceImplTest {

    @Mock
    private VueloRepository repository;

    @Mock
    private EstadoVueloRepository estadoRepository;

    @InjectMocks
    private VueloServiceImpl service;

    private Faker faker;

    @BeforeEach
    public void setUp() {
        faker = new Faker();
    }

    @Test
    public void testGetAll() {
        // Arrange
        EstadoVuelo estado = new EstadoVuelo(1L, "PROGRAMADO");
        Vuelo v1 = new Vuelo(1L, 10L, 20L, 30L, 40L, LocalDate.now(), LocalDateTime.now().plusHours(2), 120, estado);
        Vuelo v2 = new Vuelo(2L, 11L, 21L, 31L, 41L, LocalDate.now(), LocalDateTime.now().plusHours(3), 180, estado);
        when(repository.findAll()).thenReturn(Arrays.asList(v1, v2));

        // Act
        List<VueloDTO> result = service.getAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(v1.getId(), result.get(0).getId());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGetById_Success() {
        // Arrange
        Long id = 1L;
        EstadoVuelo estado = new EstadoVuelo(1L, "PROGRAMADO");
        Vuelo vuelo = new Vuelo(id, 10L, 20L, 30L, 40L, LocalDate.now(), LocalDateTime.now().plusHours(2), 120, estado);
        when(repository.findById(id)).thenReturn(Optional.of(vuelo));

        // Act
        VueloDTO result = service.getById(id);

        // Assert
        assertNotNull(result);
        assertEquals(id, result.getId());
        assertEquals("PROGRAMADO", result.getNombreEstado());
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
    public void testSearch_AllParams() {
        // Arrange
        LocalDate fecha = LocalDate.now();
        Long idOrigen = 1L;
        Long idDestino = 2L;
        EstadoVuelo estado = new EstadoVuelo(1L, "PROGRAMADO");
        Vuelo vuelo = new Vuelo(1L, 10L, 20L, idOrigen, idDestino, fecha, LocalDateTime.now().plusHours(2), 120, estado);
        when(repository.findByFechaAndIdAeropuertoOrigenAndIdAeropuertoDestino(fecha, idOrigen, idDestino))
                .thenReturn(Arrays.asList(vuelo));

        // Act
        List<VueloDTO> result = service.search(fecha, idOrigen, idDestino);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(idOrigen, result.get(0).getIdAeropuertoOrigen());
        assertEquals(idDestino, result.get(0).getIdAeropuertoDestino());
        verify(repository, times(1)).findByFechaAndIdAeropuertoOrigenAndIdAeropuertoDestino(fecha, idOrigen, idDestino);
    }

    @Test
    public void testSearch_OriginAndDest() {
        // Arrange
        Long idOrigen = 1L;
        Long idDestino = 2L;
        EstadoVuelo estado = new EstadoVuelo(1L, "PROGRAMADO");
        Vuelo vuelo = new Vuelo(1L, 10L, 20L, idOrigen, idDestino, LocalDate.now(), LocalDateTime.now().plusHours(2), 120, estado);
        when(repository.findByIdAeropuertoOrigenAndIdAeropuertoDestino(idOrigen, idDestino))
                .thenReturn(Arrays.asList(vuelo));

        // Act
        List<VueloDTO> result = service.search(null, idOrigen, idDestino);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findByIdAeropuertoOrigenAndIdAeropuertoDestino(idOrigen, idDestino);
    }

    @Test
    public void testSearch_OnlyFecha() {
        // Arrange
        LocalDate fecha = LocalDate.now();
        EstadoVuelo estado = new EstadoVuelo(1L, "PROGRAMADO");
        Vuelo vuelo = new Vuelo(1L, 10L, 20L, 1L, 2L, fecha, LocalDateTime.now().plusHours(2), 120, estado);
        when(repository.findByFecha(fecha)).thenReturn(Arrays.asList(vuelo));

        // Act
        List<VueloDTO> result = service.search(fecha, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findByFecha(fecha);
    }

    @Test
    public void testSearch_NoParams() {
        // Arrange
        EstadoVuelo estado = new EstadoVuelo(1L, "PROGRAMADO");
        Vuelo v = new Vuelo(1L, 10L, 20L, 1L, 2L, LocalDate.now(), LocalDateTime.now().plusHours(2), 120, estado);
        when(repository.findAll()).thenReturn(Arrays.asList(v));

        // Act
        List<VueloDTO> result = service.search(null, null, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testSave_Success() {
        // Arrange
        LocalDate fecha = LocalDate.now().plusDays(2);
        LocalDateTime hora = LocalDateTime.now().plusDays(2);
        VueloDTO dto = new VueloDTO(null, 10L, 20L, 3L, 4L, fecha, hora, 120, 1L, null);

        EstadoVuelo estado = new EstadoVuelo(1L, "PROGRAMADO");
        Vuelo savedEntity = new Vuelo(1L, 10L, 20L, 3L, 4L, fecha, hora, 120, estado);

        when(estadoRepository.findById(1L)).thenReturn(Optional.of(estado));
        when(repository.save(any(Vuelo.class))).thenReturn(savedEntity);

        // Act
        VueloDTO result = service.save(dto);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("PROGRAMADO", result.getNombreEstado());
        verify(estadoRepository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(Vuelo.class));
    }

    @Test
    public void testSave_EstadoNotFound() {
        // Arrange
        VueloDTO dto = new VueloDTO(null, 10L, 20L, 3L, 4L, LocalDate.now(), LocalDateTime.now(), 120, 99L, null);
        when(estadoRepository.findById(99L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.save(dto));
        verify(estadoRepository, times(1)).findById(99L);
        verify(repository, never()).save(any(Vuelo.class));
    }

    @Test
    public void testUpdate_Success() {
        // Arrange
        Long id = 1L;
        LocalDate fecha = LocalDate.now().plusDays(2);
        LocalDateTime hora = LocalDateTime.now().plusDays(2);
        VueloDTO dto = new VueloDTO(id, 10L, 20L, 3L, 4L, fecha, hora, 150, 2L, null);

        EstadoVuelo oldEstado = new EstadoVuelo(1L, "PROGRAMADO");
        Vuelo existingEntity = new Vuelo(id, 10L, 20L, 3L, 4L, fecha, hora, 120, oldEstado);

        EstadoVuelo newEstado = new EstadoVuelo(2L, "RETRASADO");
        Vuelo updatedEntity = new Vuelo(id, 10L, 20L, 3L, 4L, fecha, hora, 150, newEstado);

        when(repository.findById(id)).thenReturn(Optional.of(existingEntity));
        when(estadoRepository.findById(2L)).thenReturn(Optional.of(newEstado));
        when(repository.save(any(Vuelo.class))).thenReturn(updatedEntity);

        // Act
        VueloDTO result = service.update(id, dto);

        // Assert
        assertNotNull(result);
        assertEquals(150, result.getDuracionEstimadaMinutos());
        assertEquals("RETRASADO", result.getNombreEstado());
        verify(repository, times(1)).findById(id);
        verify(estadoRepository, times(1)).findById(2L);
        verify(repository, times(1)).save(any(Vuelo.class));
    }

    @Test
    public void testUpdate_VueloNotFound() {
        // Arrange
        Long id = 99L;
        VueloDTO dto = new VueloDTO(id, 10L, 20L, 3L, 4L, LocalDate.now(), LocalDateTime.now(), 120, 1L, null);
        when(repository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> service.update(id, dto));
        verify(repository, times(1)).findById(id);
        verify(estadoRepository, never()).findById(anyLong());
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
}

package cl.skyscraper.clients.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import cl.skyscraper.clients.dto.PassengerRequestDTO;
import cl.skyscraper.clients.exception.DuplicateResourceException;
import cl.skyscraper.clients.exception.ResourceNotFoundException;
import cl.skyscraper.clients.model.Passenger;
import cl.skyscraper.clients.repository.PassengerRepository;

@ExtendWith(MockitoExtension.class)
class PassengerServiceTest {

    @Mock
    private PassengerRepository passengerRepository;

    @InjectMocks
    private PassengerService passengerService;

    @Test
    void findAll_shouldReturnAllPassengers() {
        Passenger passenger = buildPassenger(1L, 11111111);
        when(passengerRepository.findAll()).thenReturn(List.of(passenger));

        List<Passenger> result = passengerService.findAll();

        assertEquals(1, result.size());
        assertEquals(passenger.getId(), result.get(0).getId());
    }

    @Test
    void findById_shouldReturnPassenger_whenFound() {
        Passenger passenger = buildPassenger(1L, 11111111);
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(passenger));

        Passenger result = passengerService.findById(1L);

        assertEquals(passenger.getId(), result.getId());
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        when(passengerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> passengerService.findById(1L));
    }

    @Test
    void createPassenger_shouldSavePassenger() {
        Passenger passenger = buildPassenger(null, 22222222);
        when(passengerRepository.save(any(Passenger.class))).thenReturn(passenger);

        Passenger result = passengerService.createPassenger(passenger);

        assertSame(passenger, result);
        verify(passengerRepository).save(passenger);
    }

    @Test
    void updatePassenger_shouldSaveWhenRunAndEmailAreUnique() {
        Passenger existing = buildPassenger(1L, 11111111);
        PassengerRequestDTO requestDTO = PassengerRequestDTO.builder()
                .run(22222222)
                .dv("2")
                .firstName("Jane")
                .secondName("Doe")
                .paternalLastName("Doe")
                .maternalLastName("Smith")
                .birthDate(LocalDate.of(1995, 5, 12))
                .phone("+56912345678")
                .email("new@example.com")
                .build();

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(passengerRepository.findByRun(22222222)).thenReturn(Optional.empty());
        when(passengerRepository.findByEmail("new@example.com")).thenReturn(Optional.empty());
        when(passengerRepository.save(existing)).thenReturn(existing);

        Passenger result = passengerService.updatePassenger(1L, requestDTO);

        assertEquals(22222222, result.getRun());
        assertEquals("new@example.com", result.getEmail());
        verify(passengerRepository).save(existing);
    }

    @Test
    void updatePassenger_shouldThrowWhenRunAlreadyExists() {
        Passenger existing = buildPassenger(1L, 11111111);
        PassengerRequestDTO requestDTO = PassengerRequestDTO.builder()
                .run(22222222)
                .email(existing.getEmail())
                .build();

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(passengerRepository.findByRun(22222222)).thenReturn(Optional.of(buildPassenger(2L, 22222222)));

        assertThrows(DuplicateResourceException.class, () -> passengerService.updatePassenger(1L, requestDTO));
    }

    @Test
    void updatePassenger_shouldThrowWhenEmailAlreadyExists() {
        Passenger existing = buildPassenger(1L, 11111111);
        PassengerRequestDTO requestDTO = PassengerRequestDTO.builder()
                .run(existing.getRun())
                .email("duplicate@example.com")
                .build();

        when(passengerRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(passengerRepository.findByEmail("duplicate@example.com")).thenReturn(Optional.of(buildPassenger(2L, 33333333)));

        assertThrows(DuplicateResourceException.class, () -> passengerService.updatePassenger(1L, requestDTO));
    }

    @Test
    void deletePassenger_shouldRemovePassenger() {
        Passenger existing = buildPassenger(1L, 11111111);
        when(passengerRepository.findById(1L)).thenReturn(Optional.of(existing));

        passengerService.deletePassenger(1L);

        verify(passengerRepository).delete(existing);
    }

    private Passenger buildPassenger(Long id, Integer run) {
        return Passenger.builder()
                .id(id)
                .run(run)
                .dv("1")
                .firstName("John")
                .secondName("Doe")
                .paternalLastName("Doe")
                .maternalLastName("Smith")
                .birthDate(LocalDate.of(1990, 2, 2))
                .phone("+56912345678")
                .email("passenger" + run + "@example.com")
                .build();
    }
}

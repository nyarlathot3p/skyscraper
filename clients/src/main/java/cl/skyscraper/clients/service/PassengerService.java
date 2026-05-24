package cl.skyscraper.clients.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.skyscraper.clients.dto.PassengerRequestDTO;
import cl.skyscraper.clients.exception.DuplicateResourceException;
import cl.skyscraper.clients.exception.ResourceNotFoundException;
import cl.skyscraper.clients.model.Passenger;
import cl.skyscraper.clients.repository.PassengerRepository;
import cl.skyscraper.clients.util.Messages;

@Service
public class PassengerService {

    @Autowired
    private PassengerRepository passengerRepository;

    public List<Passenger> findAll() {
        return passengerRepository.findAll();
    }

    public Passenger findById(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));
        return passenger;
    }

    public Passenger createPassenger(Passenger request) {
        Passenger savedPassenger = passengerRepository.save(request);
        return savedPassenger;
    }

    public Passenger updatePassenger(Long id, PassengerRequestDTO requestDTO) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));

        // Check if RUN is being changed and if the new RUN already exists
        if (!passenger.getRun().equals(requestDTO.getRun())) {
            if (passengerRepository.findByRun(requestDTO.getRun()).isPresent()) {
                throw new DuplicateResourceException(String.format(Messages.PASSENGER_RUN_ALREADY_EXISTS, requestDTO.getRun()));
            }
        }

        // Check if email is being changed and if the new email already exists
        if (!passenger.getEmail().equals(requestDTO.getEmail())) {
            if (passengerRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
                throw new DuplicateResourceException(String.format(Messages.PASSENGER_EMAIL_ALREADY_EXISTS, requestDTO.getEmail()));
            }
        }

        updatePassengerFromDTO(passenger, requestDTO);
        Passenger updatedPassenger = passengerRepository.save(passenger);
        return updatedPassenger;
    }

    public void deletePassenger(Long id) {
        Passenger passenger = passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger not found with id: " + id));
        passengerRepository.delete(passenger);
    }

    ///////////////////////////////////////////

    private void updatePassengerFromDTO(Passenger passenger, PassengerRequestDTO requestDTO) {
        if (requestDTO.getRun() != null) {
            passenger.setRun(requestDTO.getRun());
        }
        if (requestDTO.getDv() != null) {
            passenger.setDv(requestDTO.getDv());
        }
        if (requestDTO.getFirstName() != null) {
            passenger.setFirstName(requestDTO.getFirstName());
        }
        if (requestDTO.getSecondName() != null) {
            passenger.setSecondName(requestDTO.getSecondName());
        }
        if (requestDTO.getPaternalLastName() != null) {
            passenger.setPaternalLastName(requestDTO.getPaternalLastName());
        }
        if (requestDTO.getMaternalLastName() != null) {
            passenger.setMaternalLastName(requestDTO.getMaternalLastName());
        }
        if (requestDTO.getBirthDate() != null) {
            passenger.setBirthDate(requestDTO.getBirthDate());
        }
        if (requestDTO.getPhone() != null) {
            passenger.setPhone(requestDTO.getPhone());
        }
        if (requestDTO.getEmail() != null) {
            passenger.setEmail(requestDTO.getEmail());
        }
    }

}

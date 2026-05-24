package cl.skyscraper.clients.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.skyscraper.clients.dto.PassengerRequestDTO;
import cl.skyscraper.clients.model.Passenger;
import cl.skyscraper.clients.service.PassengerService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api_clients/v1/passengers")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    // ok
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.findAll());
    }

    // ok
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@PathVariable Long id) {
        return ResponseEntity.ok(passengerService.findById(id));
    }

    // ok  
    @PostMapping
    public ResponseEntity<Passenger> createPassenger(
            @Valid @RequestBody Passenger request) {
        return new ResponseEntity<>(passengerService.createPassenger(request), HttpStatus.CREATED);
    }

    // ok
    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(
            @PathVariable Long id,
            @Valid @RequestBody PassengerRequestDTO request) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, request));
    }

    // ok
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

}

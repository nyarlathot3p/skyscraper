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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.skyscraper.clients.dto.PassengerRequestDTO;
import cl.skyscraper.clients.model.Passenger;
import cl.skyscraper.clients.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api_clients/v1/passengers")
@Tag(name = "Pasajeros", description = "Endpoints para el manejo de pasajeros")
public class PassengerController {

    @Autowired
    private PassengerService passengerService;

    // ok
    @Operation(summary = "Listar todos los pasajeros", description = "Devuelve la lista completa de pasajeros registrados")
    @ApiResponse(responseCode = "200", description = "Pasajeros obtenidos correctamente")
    @GetMapping
    public ResponseEntity<List<Passenger>> getAllPassengers() {
        return ResponseEntity.ok(passengerService.findAll());
    }

    // ok
    @Operation(summary = "Obtener un pasajero por id", description = "Devuelve el pasajero que coincide con el identificador proporcionado")
    @ApiResponse(responseCode = "200", description = "Pasajero encontrado")
    @ApiResponse(responseCode = "404", description = "Pasajero no encontrado")
    @GetMapping("/{id}")
    public ResponseEntity<Passenger> getPassengerById(@Parameter(description = "Passenger id", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(passengerService.findById(id));
    }

    // ok  
    @Operation(summary = "Crear un pasajero", description = "Registra un nuevo pasajero en el sistema")
    @ApiResponse(responseCode = "201", description = "Pasajero creado correctamente")
    @ApiResponse(responseCode = "400", description = "Body de solicitud inválido")
    @PostMapping
    public ResponseEntity<Passenger> createPassenger(
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del pasajero a crear", required = true) @org.springframework.web.bind.annotation.RequestBody Passenger request) {
        return new ResponseEntity<>(passengerService.createPassenger(request), HttpStatus.CREATED);
    }

    // ok
    @Operation(summary = "Actualizar un pasajero", description = "Actualiza los datos de un pasajero existente")
    @ApiResponse(responseCode = "200", description = "Pasajero actualizado correctamente")
    @ApiResponse(responseCode = "404", description = "Pasajero no encontrado")
    @PutMapping("/{id}")
    public ResponseEntity<Passenger> updatePassenger(
            @Parameter(description = "Id del pasajero", required = true) @PathVariable Long id,
            @Valid @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del pasajero a actualizar", required = true) @org.springframework.web.bind.annotation.RequestBody PassengerRequestDTO request) {
        return ResponseEntity.ok(passengerService.updatePassenger(id, request));
    }

    // ok
    @Operation(summary = "Eliminar un pasajero", description = "Elimina un pasajero existente del sistema")
    @ApiResponse(responseCode = "204", description = "Pasajero eliminado correctamente")
    @ApiResponse(responseCode = "404", description = "Pasajero no encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePassenger(@Parameter(description = "Id del pasajero", required = true) @PathVariable Long id) {
        passengerService.deletePassenger(id);
        return ResponseEntity.noContent().build();
    }

}

package com.example.MS_reservas.controller;

import com.example.MS_reservas.model.dto.TicketDTO;
import com.example.MS_reservas.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService service;

    @GetMapping
    public ResponseEntity<List<TicketDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<TicketDTO> save(@Valid @RequestBody TicketDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PostMapping("/comprar")
    public ResponseEntity<TicketDTO> comprarTicket(@Valid @RequestBody com.example.MS_reservas.model.dto.ReservaRequestDTO request) {
        return new ResponseEntity<>(service.comprarTicket(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

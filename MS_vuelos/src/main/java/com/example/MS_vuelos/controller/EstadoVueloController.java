package com.example.MS_vuelos.controller;

import com.example.MS_vuelos.model.dto.EstadoVueloDTO;
import com.example.MS_vuelos.service.EstadoVueloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estados-vuelo")
public class EstadoVueloController {

    @Autowired
    private EstadoVueloService service;

    @GetMapping
    public ResponseEntity<List<EstadoVueloDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoVueloDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<EstadoVueloDTO> save(@Valid @RequestBody EstadoVueloDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoVueloDTO> update(@PathVariable Long id, @Valid @RequestBody EstadoVueloDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

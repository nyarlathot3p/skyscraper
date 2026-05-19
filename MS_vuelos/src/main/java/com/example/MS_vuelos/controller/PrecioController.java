package com.example.MS_vuelos.controller;

import com.example.MS_vuelos.model.dto.PrecioDTO;
import com.example.MS_vuelos.service.PrecioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/precios")
public class PrecioController {

    @Autowired
    private PrecioService service;

    @GetMapping
    public ResponseEntity<List<PrecioDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrecioDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/asiento/{idAsiento}")
    public ResponseEntity<List<PrecioDTO>> getByAsiento(@PathVariable Long idAsiento) {
        return ResponseEntity.ok(service.getByAsiento(idAsiento));
    }

    @PostMapping
    public ResponseEntity<PrecioDTO> save(@Valid @RequestBody PrecioDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrecioDTO> update(@PathVariable Long id, @Valid @RequestBody PrecioDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

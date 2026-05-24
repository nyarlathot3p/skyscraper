package com.example.MS_vuelos.controller;

import com.example.MS_vuelos.model.dto.VueloDTO;
import com.example.MS_vuelos.service.VueloService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/vuelos")
public class VueloController {

    @Autowired
    private VueloService service;

    @GetMapping
    public ResponseEntity<List<VueloDTO>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VueloDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/buscar")
    public ResponseEntity<List<VueloDTO>> search(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha,
            @RequestParam(required = false) Long idOrigen,
            @RequestParam(required = false) Long idDestino) {
        return ResponseEntity.ok(service.search(fecha, idOrigen, idDestino));
    }

    @PostMapping
    public ResponseEntity<VueloDTO> save(@Valid @RequestBody VueloDTO dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VueloDTO> update(@PathVariable Long id, @Valid @RequestBody VueloDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

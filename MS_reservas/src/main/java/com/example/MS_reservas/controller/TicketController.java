package com.example.MS_reservas.controller;

import com.example.MS_reservas.model.dto.TicketDTO;
import com.example.MS_reservas.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.MS_reservas.controller.assembler.TicketModelAssembler;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Validated
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService service;

    @Autowired
    private TicketModelAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<TicketDTO>>> getAll() {
        List<TicketDTO> tickets = service.getAll();
        List<EntityModel<TicketDTO>> ticketModels = tickets.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<EntityModel<TicketDTO>> collectionModel = CollectionModel.of(ticketModels,
                linkTo(methodOn(TicketController.class).getAll()).withSelfRel().withType("GET"));

        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<TicketDTO>> getById(@PathVariable Long id) {
        TicketDTO dto = service.getById(id);
        return ResponseEntity.ok(assembler.toModel(dto));
    }

    @PostMapping
    public ResponseEntity<EntityModel<TicketDTO>> save(@Valid @RequestBody TicketDTO dto) {
        TicketDTO saved = service.save(dto);
        return new ResponseEntity<>(assembler.toModel(saved), HttpStatus.CREATED);
    }

    @PostMapping("/comprar")
    public ResponseEntity<EntityModel<TicketDTO>> comprarTicket(@Valid @RequestBody com.example.MS_reservas.model.dto.ReservaRequestDTO request) {
        TicketDTO bought = service.comprarTicket(request);
        return new ResponseEntity<>(assembler.toModel(bought), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

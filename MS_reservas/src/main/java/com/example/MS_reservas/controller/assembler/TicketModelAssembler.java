package com.example.MS_reservas.controller.assembler;

import com.example.MS_reservas.controller.TicketController;
import com.example.MS_reservas.model.dto.TicketDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TicketModelAssembler implements RepresentationModelAssembler<TicketDTO, EntityModel<TicketDTO>> {

    @Override
    public EntityModel<TicketDTO> toModel(TicketDTO dto) {
        return EntityModel.of(dto,
            linkTo(methodOn(TicketController.class).getById(dto.getId())).withSelfRel().withType("GET"),
            linkTo(methodOn(TicketController.class).delete(dto.getId())).withRel("cancelar").withType("DELETE")
        );
    }
}

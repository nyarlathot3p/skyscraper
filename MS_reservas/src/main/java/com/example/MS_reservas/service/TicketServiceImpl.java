package com.example.MS_reservas.service;

import com.example.MS_reservas.client.ClienteClient;
import com.example.MS_reservas.client.FlotaClient;
import com.example.MS_reservas.client.PagoClient;
import com.example.MS_reservas.client.VueloClient;
import com.example.MS_reservas.exception.ResourceNotFoundException;
import com.example.MS_reservas.model.dto.ReservaRequestDTO;
import com.example.MS_reservas.model.dto.TicketDTO;
import com.example.MS_reservas.model.dto.external.AsientoDTO;
import com.example.MS_reservas.model.dto.external.ClienteDTO;
import com.example.MS_reservas.model.dto.external.PagoRequestDTO;
import com.example.MS_reservas.model.dto.external.PagoResponseDTO;
import com.example.MS_reservas.model.dto.external.VueloDTO;
import com.example.MS_reservas.model.entity.Ticket;
import com.example.MS_reservas.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private TicketRepository repository;

    @Autowired
    private VueloClient vueloClient;

    @Autowired
    private ClienteClient clienteClient;

    @Autowired
    private FlotaClient flotaClient;

    @Autowired
    private PagoClient pagoClient;

    @Override
    public List<TicketDTO> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public TicketDTO getById(Long id) {
        Ticket entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket no encontrado con id: " + id));
        return convertToDTO(entity);
    }

    @Override
    public TicketDTO save(TicketDTO dto) {
        Ticket entity = convertToEntity(dto);
        return convertToDTO(repository.save(entity));
    }

    @Override
    public TicketDTO comprarTicket(ReservaRequestDTO request) {
        // 1. Validar Cliente
        try {
            ClienteDTO cliente = clienteClient.getClienteById(request.getIdComprador());
            if (cliente == null) throw new ResourceNotFoundException("Cliente no encontrado o inactivo");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error al validar cliente (9090): " + e.getMessage());
        }

        // 2. Validar Vuelo
        try {
            VueloDTO vuelo = vueloClient.getVueloById(request.getIdVuelo());
            if (vuelo == null) throw new ResourceNotFoundException("Vuelo no encontrado");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error al validar vuelo (8091): " + e.getMessage());
        }

        // 2.1 Validar Asiento en Flota
        try {
            AsientoDTO asiento = flotaClient.getAsientoById(request.getIdAsientoVuelo());
            if (asiento == null) throw new ResourceNotFoundException("Asiento no encontrado");
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error al validar asiento en flota (8082): " + e.getMessage());
        }

        // 3. Preparar e iniciar el pago
        PagoResponseDTO pagoResponse;
        try {
            PagoRequestDTO pagoRequest = new PagoRequestDTO(request.getValorTransaccion(), request.getIdMedioPago(), request.getIdComprador());
            pagoResponse = pagoClient.procesarPago(pagoRequest);
            if (pagoResponse == null || pagoResponse.getIdTransaccion() == null) {
                throw new RuntimeException("Respuesta de pago inválida");
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el pago (9091): " + e.getMessage());
        }

        // 4. Confirmar Reserva guardando el Ticket
        Ticket entity = new Ticket();
        entity.setIdComprador(request.getIdComprador());
        entity.setIdPasajero(request.getIdPasajero());
        entity.setIdAsientoVuelo(request.getIdAsientoVuelo());
        entity.setIdTransaccion(pagoResponse.getIdTransaccion());

        return convertToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ticket no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private TicketDTO convertToDTO(Ticket entity) {
        return new TicketDTO(
                entity.getId(),
                entity.getIdComprador(),
                entity.getIdPasajero(),
                entity.getIdAsientoVuelo(),
                entity.getIdTransaccion()
        );
    }

    private Ticket convertToEntity(TicketDTO dto) {
        return new Ticket(
                dto.getId(),
                dto.getIdComprador(),
                dto.getIdPasajero(),
                dto.getIdAsientoVuelo(),
                dto.getIdTransaccion()
        );
    }
}

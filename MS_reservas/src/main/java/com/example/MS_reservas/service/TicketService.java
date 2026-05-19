package com.example.MS_reservas.service;

import com.example.MS_reservas.model.dto.TicketDTO;
import java.util.List;

public interface TicketService {
    List<TicketDTO> getAll();
    TicketDTO getById(Long id);
    TicketDTO save(TicketDTO dto);
    TicketDTO comprarTicket(com.example.MS_reservas.model.dto.ReservaRequestDTO dto);
    void delete(Long id);
}

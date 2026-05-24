package com.example.MS_reservas.repository;

import com.example.MS_reservas.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByIdComprador(Long idComprador);
    List<Ticket> findByIdPasajero(Long idPasajero);
}

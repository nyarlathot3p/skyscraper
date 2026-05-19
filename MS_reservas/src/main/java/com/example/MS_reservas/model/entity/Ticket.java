package com.example.MS_reservas.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Ticket")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_comprador", nullable = false)
    private Long idComprador;

    @Column(name = "id_pasajero", nullable = false)
    private Long idPasajero;

    @Column(name = "id_asiento_vuelo", nullable = false)
    private Long idAsientoVuelo;

    @Column(name = "id_transaccion", nullable = false)
    private Long idTransaccion;
}

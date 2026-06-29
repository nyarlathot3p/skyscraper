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

    public Long getId() { return id; }
    public Long getIdComprador() { return idComprador; }
    public Long getIdPasajero() { return idPasajero; }
    public Long getIdAsientoVuelo() { return idAsientoVuelo; }
    public Long getIdTransaccion() { return idTransaccion; }

    public void setIdComprador(Long idComprador) { this.idComprador = idComprador; }
    public void setIdPasajero(Long idPasajero) { this.idPasajero = idPasajero; }
    public void setIdAsientoVuelo(Long idAsientoVuelo) { this.idAsientoVuelo = idAsientoVuelo; }
    public void setIdTransaccion(Long idTransaccion) { this.idTransaccion = idTransaccion; }
}

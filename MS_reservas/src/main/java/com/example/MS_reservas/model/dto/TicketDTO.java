package com.example.MS_reservas.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;

    @NotNull(message = "El id del comprador es obligatorio")
    @Positive(message = "El id del comprador debe ser mayor a 0")
    private Long idComprador;

    @NotNull(message = "El id del pasajero es obligatorio")
    @Positive(message = "El id del pasajero debe ser mayor a 0")
    private Long idPasajero;

    @NotNull(message = "El id del asiento/vuelo es obligatorio")
    @Positive(message = "El id del asiento/vuelo debe ser mayor a 0")
    private Long idAsientoVuelo;

    @NotNull(message = "El id de la transacción es obligatorio")
    @Positive(message = "El id de la transacción debe ser mayor a 0")
    private Long idTransaccion;

    public Long getId() { return id; }
    public Long getIdComprador() { return idComprador; }
    public Long getIdPasajero() { return idPasajero; }
    public Long getIdAsientoVuelo() { return idAsientoVuelo; }
    public Long getIdTransaccion() { return idTransaccion; }

    public TicketDTO(Long id, Long idComprador, Long idPasajero, Long idAsientoVuelo, Long idTransaccion) {
        this.id = id;
        this.idComprador = idComprador;
        this.idPasajero = idPasajero;
        this.idAsientoVuelo = idAsientoVuelo;
        this.idTransaccion = idTransaccion;
    }
}

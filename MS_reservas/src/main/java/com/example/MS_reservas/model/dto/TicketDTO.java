package com.example.MS_reservas.model.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;

    @NotNull(message = "El id del comprador es obligatorio")
    private Long idComprador;

    @NotNull(message = "El id del pasajero es obligatorio")
    private Long idPasajero;

    @NotNull(message = "El id del asiento/vuelo es obligatorio")
    private Long idAsientoVuelo;

    @NotNull(message = "El id de la transacción es obligatorio")
    private Long idTransaccion;
}

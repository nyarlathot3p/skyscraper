package com.example.MS_reservas.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {

    @NotNull(message = "El id del comprador es obligatorio")
    @Positive(message = "El id del comprador debe ser mayor a 0")
    private Long idComprador;

    @NotNull(message = "El id del pasajero es obligatorio")
    @Positive(message = "El id del pasajero debe ser mayor a 0")
    private Long idPasajero;

    @NotNull(message = "El id del asiento/vuelo es obligatorio")
    @Positive(message = "El id del asiento/vuelo debe ser mayor a 0")
    private Long idAsientoVuelo;

    @NotNull(message = "El id del vuelo es obligatorio")
    @Positive(message = "El id del vuelo debe ser mayor a 0")
    private Long idVuelo;

    @NotNull(message = "El valor de la transacción es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor de la transacción debe ser mayor a 0")
    private Double valorTransaccion;

    @NotNull(message = "El id del medio de pago es obligatorio")
    @Positive(message = "El id del medio de pago debe ser mayor a 0")
    private Long idMedioPago;

    public Long getIdComprador() {
        return idComprador;
    }

    public Long getIdPasajero() {
        return idPasajero;
    }

    public Long getIdAsientoVuelo() {
        return idAsientoVuelo;
    }

    public Long getIdVuelo() {
        return idVuelo;
    }

    public Double getValorTransaccion() {
        return valorTransaccion;
    }

    public Long getIdMedioPago() {
        return idMedioPago;
    }
}

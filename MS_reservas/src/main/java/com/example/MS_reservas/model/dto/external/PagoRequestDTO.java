package com.example.MS_reservas.model.dto.external;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {
    @NotNull(message = "El valor de la transacción es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor de la transacción debe ser mayor a 0")
    private Double valorTransaccion;

    @NotNull(message = "El id del medio de pago es obligatorio")
    @Positive(message = "El id del medio de pago debe ser mayor a 0")
    private Long idMedioPago;

    @NotNull(message = "El id del comprador es obligatorio")
    @Positive(message = "El id del comprador debe ser mayor a 0")
    private Long idComprador;

}

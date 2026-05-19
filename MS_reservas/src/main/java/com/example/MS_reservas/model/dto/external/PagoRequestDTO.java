package com.example.MS_reservas.model.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {
    private Double valorTransaccion;
    private Long idMedioPago;
    private Long idComprador;
}

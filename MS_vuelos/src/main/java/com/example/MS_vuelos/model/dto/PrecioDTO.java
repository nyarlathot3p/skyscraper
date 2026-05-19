package com.example.MS_vuelos.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrecioDTO {

    private Long id;

    @NotNull(message = "El id del asiento es obligatorio")
    private Long idAsiento;

    @NotNull(message = "El precio actual es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precioActual;

    private LocalDate fechaCaptura;

    private String linkExterno;
}

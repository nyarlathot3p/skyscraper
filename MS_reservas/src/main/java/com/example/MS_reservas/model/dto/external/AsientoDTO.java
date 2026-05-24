package com.example.MS_reservas.model.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoDTO {
    
    @NotNull(message = "El id del asiento es obligatorio")
    @Positive(message = "El id del asiento debe ser mayor a 0")
    private Long id;

    @NotNull(message = "La fila es obligatoria")
    @Positive(message = "La fila debe ser mayor a 0")
    private Integer fila;

    @NotBlank(message = "La letra del asiento no puede estar vacía")
    private String letra;

    @NotNull(message = "El id de la clase es obligatorio")
    @Positive(message = "El id de la clase debe ser mayor a 0")
    private Long idClase;

    @NotNull(message = "El id de la posición es obligatorio")
    @Positive(message = "El id de la posición debe ser mayor a 0")
    private Long idPosicion;

    @NotNull(message = "El id del avión es obligatorio")
    @Positive(message = "El id del avión debe ser mayor a 0")
    private Long idAvion;
}

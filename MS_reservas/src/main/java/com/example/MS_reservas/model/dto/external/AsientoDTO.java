package com.example.MS_reservas.model.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsientoDTO {
    private Long id;
    private Integer fila;
    private String letra;
    private Long idClase;
    private Long idPosicion;
    private Long idAvion;
    // Opcional: algún campo de estado si el MS de Flota lo maneja (ej. "ocupado" = true/false)
}

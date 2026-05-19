package com.example.MS_reservas.model.dto.external;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VueloDTO {
    private Long id;
    private Long idAvion;
    private Long idAerolinea;
    private Long idAeropuertoOrigen;
    private Long idAeropuertoDestino;
    private LocalDate fecha;
    private LocalDateTime horaPartida;
    private Integer duracionEstimadaMinutos;
    private Long idEstado;
    private String nombreEstado;
}

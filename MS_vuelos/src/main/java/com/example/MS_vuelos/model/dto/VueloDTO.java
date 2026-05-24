package com.example.MS_vuelos.model.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VueloDTO {

    private Long id;

    @NotNull(message = "El id del avión es obligatorio")
    private Long idAvion;

    @NotNull(message = "El id de la aerolínea es obligatorio")
    private Long idAerolinea;

    @NotNull(message = "El id del aeropuerto de origen es obligatorio")
    private Long idAeropuertoOrigen;

    @NotNull(message = "El id del aeropuerto de destino es obligatorio")
    private Long idAeropuertoDestino;

    @NotNull(message = "La fecha del vuelo es obligatoria")
    @FutureOrPresent(message = "La fecha del vuelo debe ser hoy o futura")
    private LocalDate fecha;

    @NotNull(message = "La hora de partida es obligatoria")
    @FutureOrPresent(message = "La hora de partida debe ser presente o futura")
    private LocalDateTime horaPartida;

    @Positive(message = "La duración estimada debe ser mayor a 0")
    private Integer duracionEstimadaMinutos;

    @NotNull(message = "El id del estado es obligatorio")
    private Long idEstado;
    
    private String nombreEstado; // Para conveniencia en la respuesta
}

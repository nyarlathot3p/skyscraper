package com.example.MS_reservas.model.dto.external;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class VueloDTO {
    private Long id;

    @NotNull(message = "El id del avión es obligatorio")
    @Positive(message = "El id del avión debe ser mayor a 0")
    private Long idAvion;

    @NotNull(message = "El id de la aerolínea es obligatorio")
    @Positive(message = "El id de la aerolínea debe ser mayor a 0")
    private Long idAerolinea;

    @NotNull(message = "El id del aeropuerto de origen es obligatorio")
    @Positive(message = "El id del aeropuerto de origen debe ser mayor a 0")
    private Long idAeropuertoOrigen;

    @NotNull(message = "El id del aeropuerto de destino es obligatorio")
    @Positive(message = "El id del aeropuerto de destino debe ser mayor a 0")
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
    @Positive(message = "El id del estado debe ser mayor a 0")
    private Long idEstado;

    private String nombreEstado;
}

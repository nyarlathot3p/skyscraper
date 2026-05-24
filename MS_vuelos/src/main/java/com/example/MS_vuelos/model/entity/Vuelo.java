package com.example.MS_vuelos.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Vuelo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vuelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_avion", nullable = false)
    private Long idAvion;

    @Column(name = "id_aerolinea", nullable = false)
    private Long idAerolinea;

    @Column(name = "id_aeropuerto_origen", nullable = false)
    private Long idAeropuertoOrigen;

    @Column(name = "id_aeropuerto_destino", nullable = false)
    private Long idAeropuertoDestino;

    private LocalDate fecha;

    @Column(name = "hora_partida")
    private LocalDateTime horaPartida;

    @Column(name = "duracion_estimada_minutos")
    private Integer duracionEstimadaMinutos;

    @ManyToOne
    @JoinColumn(name = "id_estado", nullable = false)
    private EstadoVuelo estado;
}

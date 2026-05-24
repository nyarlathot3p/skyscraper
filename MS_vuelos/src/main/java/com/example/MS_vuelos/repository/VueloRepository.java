package com.example.MS_vuelos.repository;

import com.example.MS_vuelos.model.entity.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Long> {
    List<Vuelo> findByFecha(LocalDate fecha);
    List<Vuelo> findByIdAeropuertoOrigenAndIdAeropuertoDestino(Long idOrigen, Long idDestino);
    List<Vuelo> findByFechaAndIdAeropuertoOrigenAndIdAeropuertoDestino(LocalDate fecha, Long idOrigen, Long idDestino);
}

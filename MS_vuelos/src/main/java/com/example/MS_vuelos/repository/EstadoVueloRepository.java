package com.example.MS_vuelos.repository;

import com.example.MS_vuelos.model.entity.EstadoVuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoVueloRepository extends JpaRepository<EstadoVuelo, Long> {
}

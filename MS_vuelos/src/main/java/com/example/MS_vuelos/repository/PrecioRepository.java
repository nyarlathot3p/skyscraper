package com.example.MS_vuelos.repository;

import com.example.MS_vuelos.model.entity.Precio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecioRepository extends JpaRepository<Precio, Long> {
    List<Precio> findByIdAsiento(Long idAsiento);
}

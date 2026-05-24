package com.example.MS_vuelos.service;

import com.example.MS_vuelos.model.dto.EstadoVueloDTO;
import java.util.List;

public interface EstadoVueloService {
    List<EstadoVueloDTO> getAll();
    EstadoVueloDTO getById(Long id);
    EstadoVueloDTO save(EstadoVueloDTO dto);
    EstadoVueloDTO update(Long id, EstadoVueloDTO dto);
    void delete(Long id);
}

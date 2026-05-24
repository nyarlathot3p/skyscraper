package com.example.MS_vuelos.service;

import com.example.MS_vuelos.model.dto.PrecioDTO;
import java.util.List;

public interface PrecioService {
    List<PrecioDTO> getAll();
    PrecioDTO getById(Long id);
    List<PrecioDTO> getByAsiento(Long idAsiento);
    PrecioDTO save(PrecioDTO dto);
    PrecioDTO update(Long id, PrecioDTO dto);
    void delete(Long id);
}

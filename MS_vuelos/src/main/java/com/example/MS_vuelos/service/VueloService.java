package com.example.MS_vuelos.service;

import com.example.MS_vuelos.model.dto.VueloDTO;
import java.time.LocalDate;
import java.util.List;

public interface VueloService {
    List<VueloDTO> getAll();
    VueloDTO getById(Long id);
    List<VueloDTO> search(LocalDate fecha, Long idOrigen, Long idDestino);
    VueloDTO save(VueloDTO dto);
    VueloDTO update(Long id, VueloDTO dto);
    void delete(Long id);
}

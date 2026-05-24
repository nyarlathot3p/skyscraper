package com.example.MS_vuelos.service;

import com.example.MS_vuelos.exception.ResourceNotFoundException;
import com.example.MS_vuelos.model.dto.EstadoVueloDTO;
import com.example.MS_vuelos.model.entity.EstadoVuelo;
import com.example.MS_vuelos.repository.EstadoVueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoVueloServiceImpl implements EstadoVueloService {

    @Autowired
    private EstadoVueloRepository repository;

    @Override
    public List<EstadoVueloDTO> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EstadoVueloDTO getById(Long id) {
        EstadoVuelo entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de vuelo no encontrado con id: " + id));
        return convertToDTO(entity);
    }

    @Override
    public EstadoVueloDTO save(EstadoVueloDTO dto) {
        EstadoVuelo entity = convertToEntity(dto);
        return convertToDTO(repository.save(entity));
    }

    @Override
    public EstadoVueloDTO update(Long id, EstadoVueloDTO dto) {
        EstadoVuelo entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Estado de vuelo no encontrado con id: " + id));
        entity.setNombre(dto.getNombre());
        return convertToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Estado de vuelo no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private EstadoVueloDTO convertToDTO(EstadoVuelo entity) {
        return new EstadoVueloDTO(entity.getId(), entity.getNombre());
    }

    private EstadoVuelo convertToEntity(EstadoVueloDTO dto) {
        return new EstadoVuelo(dto.getId(), dto.getNombre());
    }
}

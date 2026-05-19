package com.example.MS_vuelos.service;

import com.example.MS_vuelos.exception.ResourceNotFoundException;
import com.example.MS_vuelos.model.dto.PrecioDTO;
import com.example.MS_vuelos.model.entity.Precio;
import com.example.MS_vuelos.repository.PrecioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrecioServiceImpl implements PrecioService {

    @Autowired
    private PrecioRepository repository;

    @Override
    public List<PrecioDTO> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrecioDTO getById(Long id) {
        Precio entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Precio no encontrado con id: " + id));
        return convertToDTO(entity);
    }

    @Override
    public List<PrecioDTO> getByAsiento(Long idAsiento) {
        return repository.findByIdAsiento(idAsiento).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public PrecioDTO save(PrecioDTO dto) {
        Precio entity = convertToEntity(dto);
        return convertToDTO(repository.save(entity));
    }

    @Override
    public PrecioDTO update(Long id, PrecioDTO dto) {
        Precio entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Precio no encontrado con id: " + id));
        
        entity.setIdAsiento(dto.getIdAsiento());
        entity.setPrecioActual(dto.getPrecioActual());
        entity.setFechaCaptura(dto.getFechaCaptura());
        entity.setLinkExterno(dto.getLinkExterno());

        return convertToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Precio no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private PrecioDTO convertToDTO(Precio entity) {
        return new PrecioDTO(
                entity.getId(),
                entity.getIdAsiento(),
                entity.getPrecioActual(),
                entity.getFechaCaptura(),
                entity.getLinkExterno()
        );
    }

    private Precio convertToEntity(PrecioDTO dto) {
        return new Precio(
                dto.getId(),
                dto.getIdAsiento(),
                dto.getPrecioActual(),
                dto.getFechaCaptura(),
                dto.getLinkExterno()
        );
    }
}

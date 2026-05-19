package com.example.MS_vuelos.service;

import com.example.MS_vuelos.exception.ResourceNotFoundException;
import com.example.MS_vuelos.model.dto.VueloDTO;
import com.example.MS_vuelos.model.entity.EstadoVuelo;
import com.example.MS_vuelos.model.entity.Vuelo;
import com.example.MS_vuelos.repository.EstadoVueloRepository;
import com.example.MS_vuelos.repository.VueloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VueloServiceImpl implements VueloService {

    @Autowired
    private VueloRepository repository;

    @Autowired
    private EstadoVueloRepository estadoRepository;

    @Override
    public List<VueloDTO> getAll() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public VueloDTO getById(Long id) {
        Vuelo entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo no encontrado con id: " + id));
        return convertToDTO(entity);
    }

    @Override
    public List<VueloDTO> search(LocalDate fecha, Long idOrigen, Long idDestino) {
        List<Vuelo> vuelos;
        if (fecha != null && idOrigen != null && idDestino != null) {
            vuelos = repository.findByFechaAndIdAeropuertoOrigenAndIdAeropuertoDestino(fecha, idOrigen, idDestino);
        } else if (idOrigen != null && idDestino != null) {
            vuelos = repository.findByIdAeropuertoOrigenAndIdAeropuertoDestino(idOrigen, idDestino);
        } else if (fecha != null) {
            vuelos = repository.findByFecha(fecha);
        } else {
            vuelos = repository.findAll();
        }
        return vuelos.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public VueloDTO save(VueloDTO dto) {
        Vuelo entity = convertToEntity(dto);
        return convertToDTO(repository.save(entity));
    }

    @Override
    public VueloDTO update(Long id, VueloDTO dto) {
        Vuelo entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vuelo no encontrado con id: " + id));
        
        EstadoVuelo estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de vuelo no encontrado con id: " + dto.getIdEstado()));

        entity.setIdAvion(dto.getIdAvion());
        entity.setIdAerolinea(dto.getIdAerolinea());
        entity.setIdAeropuertoOrigen(dto.getIdAeropuertoOrigen());
        entity.setIdAeropuertoDestino(dto.getIdAeropuertoDestino());
        entity.setFecha(dto.getFecha());
        entity.setHoraPartida(dto.getHoraPartida());
        entity.setDuracionEstimadaMinutos(dto.getDuracionEstimadaMinutos());
        entity.setEstado(estado);

        return convertToDTO(repository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Vuelo no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private VueloDTO convertToDTO(Vuelo entity) {
        return new VueloDTO(
                entity.getId(),
                entity.getIdAvion(),
                entity.getIdAerolinea(),
                entity.getIdAeropuertoOrigen(),
                entity.getIdAeropuertoDestino(),
                entity.getFecha(),
                entity.getHoraPartida(),
                entity.getDuracionEstimadaMinutos(),
                entity.getEstado().getId(),
                entity.getEstado().getNombre()
        );
    }

    private Vuelo convertToEntity(VueloDTO dto) {
        EstadoVuelo estado = estadoRepository.findById(dto.getIdEstado())
                .orElseThrow(() -> new ResourceNotFoundException("Estado de vuelo no encontrado con id: " + dto.getIdEstado()));
        
        return new Vuelo(
                dto.getId(),
                dto.getIdAvion(),
                dto.getIdAerolinea(),
                dto.getIdAeropuertoOrigen(),
                dto.getIdAeropuertoDestino(),
                dto.getFecha(),
                dto.getHoraPartida(),
                dto.getDuracionEstimadaMinutos(),
                estado
        );
    }
}

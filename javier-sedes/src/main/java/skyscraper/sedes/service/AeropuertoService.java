package skyscraper.sedes.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import skyscraper.sedes.controller.SedesController;
import skyscraper.sedes.dto.CiudadDTO;
import skyscraper.sedes.repository.AeropuertoRepository;
import skyscraper.sedes.model.Aeropuerto;

import java.util.ArrayList;
import java.util.List;

@Service
public class AeropuertoService {
    @Autowired
    private AeropuertoRepository aeropuertoRepository;
    @Autowired
    private SedesController ciudadClientRest;



//lista de aeropuertos en una ciudad especifica por nombre
    public List<Aeropuerto> getAeropuertosByCiudadNombre(String ciudadNombre) {
        Long ciudadId = ciudadClientRest.getIdByNombre(ciudadNombre);
        return aeropuertoRepository.findByCiudadId(ciudadId);
    }


//lista de aeropuertos en una region especifica por nombre
    public List<Aeropuerto> getAeropuertosByRegionNombre(String regionNombre) {
        List<CiudadDTO> ciudades = ciudadClientRest.getCiudadesByRegionNombre(regionNombre);
        List<Aeropuerto> aeropuertos = new ArrayList<>();
        for (CiudadDTO ciudad : ciudades) {
            aeropuertos.addAll(aeropuertoRepository.findByCiudadId(ciudad.getId()));
        }
        return aeropuertos;
    }



}

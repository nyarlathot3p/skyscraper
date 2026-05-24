package skyscraper.geaografia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import skyscraper.geaografia.model.Ciudad;
import skyscraper.geaografia.repository.CiudadRepository;
@Service
public class CiudadService {
    @Autowired
    private CiudadRepository ciudadRepository;

    public String getNombreById(Long id) {
        return ciudadRepository.findNombreById(id);
    }
    public Long getIdByNombre(String nombre) {
        return ciudadRepository.findIdByNombre(nombre);
    }
    public java.util.List<Ciudad> getCiudadesByRegionId(Long regionId) {
        return ciudadRepository.findByRegionId(regionId);
    }
    public java.util.List<Ciudad> getCiudadesByRegionNombre(String regionNombre) {
        return ciudadRepository.findByRegionNombre(regionNombre);
    }
    



}


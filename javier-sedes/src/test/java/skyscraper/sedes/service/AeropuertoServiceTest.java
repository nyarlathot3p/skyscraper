package skyscraper.sedes.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apiguardian.api.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skyscraper.sedes.model.Aeropuerto;
import skyscraper.sedes.repository.AeropuertoRepository;
import skyscraper.sedes.service.AeropuertoService;
import skyscraper.sedes.client.CiudadClientRest;
import skyscraper.sedes.controller.SedesController;
import skyscraper.sedes.dto.CiudadDTO;
import skyscraper.sedes.dto.RegionDTO;

import java.beans.Transient;
import java.util.List;

@SpringBootTest

public class AeropuertoServiceTest {

    @Autowired
    private AeropuertoService aeropuertoService;

    @Mock
    private AeropuertoRepository aeropuertoRepository;
    @Mock
    private CiudadClientRest ciudadClientRest;
    

    @Test
    public void testGetAeropuertosByCiudad() {
        // probar que llame el aeropuerto si existe un aeropuerto con el id de ciudad
        when(ciudadClientRest.getIdByNombre("paine")).thenReturn(1L);
        when(aeropuertoRepository.findByCiudadId(1L)).thenReturn(List.of(
                Aeropuerto.builder().id(1L).nombre("Aeropuerto 1").build(),
                Aeropuerto.builder().id(2L).nombre("Aeropuerto 2").build()
        ));
        List<Aeropuerto> aeropuertos = aeropuertoService.getAeropuertosByCiudadNombre("paine");
        assert aeropuertos != null;
        assert aeropuertos.size() == 2;
    }

    @Test
    public void testGetAeropuertosByRegion() {
        // probar que llame el aeropuerto si existe un aeropuerto con el id de region
        RegionDTO region = RegionDTO.builder().id(1L).nombre("regionTest").build();
        CiudadDTO ciudad1 = CiudadDTO.builder().id(1L).nombre("ciudad1").region(region).build();
        CiudadDTO ciudad2 = CiudadDTO.builder().id(2L).nombre("ciudad2").region(region).build();
        when(ciudadClientRest.getCiudadesByRegionNombre("regionTest")).thenReturn(List.of(ciudad1, ciudad2));

        when(aeropuertoRepository.findByCiudadId(1L)).thenReturn(List.of(
                Aeropuerto.builder().id(1L).nombre("Aeropuerto 1").build()
        ));
        when(aeropuertoRepository.findByCiudadId(2L)).thenReturn(List.of(
                Aeropuerto.builder().id(2L).nombre("Aeropuerto 2").build()
        ));
        List<Aeropuerto> aeropuertos = aeropuertoService.getAeropuertosByRegionNombre("regionTest");
        assert aeropuertos != null;
        assert aeropuertos.size() == 2;
    }
}

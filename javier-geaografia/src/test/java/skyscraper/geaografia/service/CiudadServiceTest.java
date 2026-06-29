package skyscraper.geaografia.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apiguardian.api.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skyscraper.geaografia.model.Ciudad;
import skyscraper.geaografia.repository.CiudadRepository;
import skyscraper.geaografia.model.Region;

import java.beans.Transient;
import java.util.List;

@SpringBootTest
public class CiudadServiceTest {

    
    @Autowired
    private CiudadService ciudadService;

    @Mock
    private CiudadRepository ciudadRepository;
    

    @Test
    public void testGetNombreById() {
        Long id = 1L;
        String expectedNombre = "Ciudad de Prueba";
        when(ciudadRepository.findNombreById(id)).thenReturn(expectedNombre);
        String actualNombre = ciudadService.getNombreById(id);
        assertEquals(expectedNombre, actualNombre);
    }

    @Test
    public void testGetIdByNombre() {
        String nombre = "Ciudad de Prueba";
        Long expectedId = 1L;
        when(ciudadRepository.findIdByNombre(nombre)).thenReturn(expectedId);
        Long actualId = ciudadService.getIdByNombre(nombre);
        assertEquals(expectedId, actualId);
    }

    @Test
    public void testGetCiudadesByRegionId() {
        Long regionId = 1L;
        Region regionTest = Region.builder().id(regionId).nombre("Region 1").build();
        List<Ciudad> expectedCiudades = List.of(Ciudad.builder().id(1L).nombre("Ciudad 1").region(regionTest).build(),
                Ciudad.builder().id(2L).nombre("Ciudad 2").region(regionTest).build());
        when(ciudadRepository.findByRegionId(regionId)).thenReturn(expectedCiudades);
        List<Ciudad> actualCiudades = ciudadService.getCiudadesByRegionId(regionId);
        assertEquals(expectedCiudades, actualCiudades);
    }

    @Test
    public void testGetCiudadesByRegionNombre() {
        String regionNombre = "Region 1";
        Region regionTest = Region.builder().id(1L).nombre(regionNombre).build();
        List<Ciudad> expectedCiudades = List.of(Ciudad.builder().id(1L).nombre("Ciudad 1").region(regionTest).build(),
                Ciudad.builder().id(2L).nombre("Ciudad 2").region(regionTest).build());
        when(ciudadRepository.findByRegionNombre(regionNombre)).thenReturn(expectedCiudades);
        List<Ciudad> actualCiudades = ciudadService.getCiudadesByRegionNombre(regionNombre);
        assertEquals(expectedCiudades, actualCiudades);
    }

    


}

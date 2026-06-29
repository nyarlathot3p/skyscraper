package skyscraper.puestos.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apiguardian.api.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skyscraper.puestos.model.Asiento;
import skyscraper.puestos.repository.AsientosRepository;
import skyscraper.puestos.model.Clase;
import skyscraper.puestos.model.Posicion;

import java.beans.Transient;
import java.util.List;

@SpringBootTest

public class AsientoServiceTest {
    @Autowired
    private AsientoService asientoService;

    @Mock
    private AsientosRepository asientoRepository;

    @Test
    public void testGetAsientosByClase() {
        String nombreClase = "Economica";
        Clase clase = Clase.builder().id(1L).nombre(nombreClase).build();
        List<Asiento> expectedAsientos = List.of(Asiento.builder().id(1L).clase(clase).build(), Asiento.builder().id(2L).clase(clase).build());
        when(asientoRepository.findByClaseNombre(nombreClase)).thenReturn(expectedAsientos);

        List<Asiento> actualAsientos = asientoService.getAsientosByClase(nombreClase);

        assertEquals(expectedAsientos, actualAsientos);
        verify(asientoRepository, times(1)).findByClaseNombre(nombreClase);
    }

    @Test
    public void testGetAsientosByPosicion() {
        String nombrePosicion = "Ventana";
        Posicion posicion = Posicion.builder().id(1L).nombre(nombrePosicion).build();
        List<Asiento> expectedAsientos = List.of(Asiento.builder().id(1L).posicion(posicion).build(), Asiento.builder().id(2L).posicion(posicion).build());
        when(asientoRepository.findByPosicionNombre(nombrePosicion)).thenReturn(expectedAsientos);

        List<Asiento> actualAsientos = asientoService.getAsientosByPosicion(nombrePosicion);

        assertEquals(expectedAsientos, actualAsientos);
        verify(asientoRepository, times(1)).findByPosicionNombre(nombrePosicion);
    }

    @Test
    public void testGetAsientosByFila() {
        int fila = 1;
        List<Asiento> expectedAsientos = List.of(Asiento.builder().id(1L).fila(fila).letra("A").build(), Asiento.builder().id(2L).fila(fila).letra("B").build());
        when(asientoRepository.findByFila(fila)).thenReturn(expectedAsientos);
        List<Asiento> actualAsientos = asientoService.getAsientosByFila(fila);
        assertEquals(expectedAsientos, actualAsientos);
        verify(asientoRepository, times(1)).findByFila(fila);
    }

    @Test
    public void testOcuparAsiento() {
        Long asientoId = 1L;
        doNothing().when(asientoRepository).ocuparAsiento(asientoId);

        asientoService.ocuparAsiento(asientoId);

        verify(asientoRepository, times(1)).ocuparAsiento(asientoId);
    }
    @Test
    public void testDesocuparAsiento() {
        Long asientoId = 1L;
        doNothing().when(asientoRepository).desocuparAsiento(asientoId);

        asientoService.desocuparAsiento(asientoId);

        verify(asientoRepository, times(1)).desocuparAsiento(asientoId);
    }

    @Test
    public void testGetAsientosByAvionId() {
        Long avionId = 1L;
        List<Asiento> expectedAsientos = List.of(Asiento.builder().id(1L).idAvion(avionId).build(), Asiento.builder().id(2L).idAvion(avionId).build());
        when(asientoRepository.findByAvionId(avionId)).thenReturn(expectedAsientos);
        List<Asiento> actualAsientos = asientoService.getAsientosByAvionId(avionId);
        assertEquals(expectedAsientos, actualAsientos);
        verify(asientoRepository, times(1)).findByAvionId(avionId);
    }

    @Test
    public void testGetAsientoById() {
        Long asientoId = 1L;
        Asiento expectedAsiento = Asiento.builder().id(asientoId).build();
        when(asientoRepository.findById(asientoId)).thenReturn(java.util.Optional.of(expectedAsiento));
        Asiento actualAsiento = asientoService.getAsientoById(asientoId);
        assertEquals(expectedAsiento, actualAsiento);
        verify(asientoRepository, times(1)).findById(asientoId);
    }

    @Test
    public void testGetAsientoByIdNotFound() {
        Long asientoId = 1L;
        when(asientoRepository.findById(asientoId)).thenReturn(java.util.Optional.empty());
        Asiento actualAsiento = asientoService.getAsientoById(asientoId);
        assertNull(actualAsiento);
        verify(asientoRepository, times(1)).findById(asientoId);
    }


}

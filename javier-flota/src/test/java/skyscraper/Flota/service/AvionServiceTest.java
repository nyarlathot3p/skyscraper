package skyscraper.Flota.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.apiguardian.api.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import skyscraper.Flota.clients.AsientoClientRest;
import skyscraper.Flota.dto.AsientoDTO;
import skyscraper.Flota.model.Avion;
import skyscraper.Flota.repository.AvionRepository;

import java.beans.Transient;
import java.util.List;

@SpringBootTest
public class AvionServiceTest {

    @Autowired
    private AvionService avionService;

    @Mock
    private AvionRepository avionRepository;
    @Mock
    private AsientoClientRest asientoClientRest;

    @Test
    public void testGetAvionesByAerolineaId() {
        // probar que llame el avion si existe un avion con el id de aerolinea
        when(avionRepository.findById(1L)).thenReturn(java.util.Optional.of(Avion.builder().id(1L).modelo("Boeing 737").build()));
        Avion avion = avionService.getAvionesByAerolineaId(1L).stream().findFirst().orElse(null);
        assert avion != null;
        assert avion.getId() == 1L;
        assert avion.getModelo().equals("Boeing 737");
    }

    @Test
    public void testGetAvionesByAerolineaIdEnAerolineaVacia() {
        // verificar que si no hay aviones en la aerolinea, devuelva una lista vacía
        when(avionRepository.findAvionesByAerolineaId(1L)).thenReturn(java.util.Collections.emptyList());
        List<Avion> aviones = avionService.getAvionesByAerolineaId(1L);
        assertNotNull(aviones, "La lista de aviones no debería ser nula");
        assertTrue(aviones.isEmpty(), "La lista de aviones debería estar vacía");
    }

    @Test
    public void testGetAsientosByClaseAvion(){
        // probar que llame el asiento si existe un asiento con el id de avion y clase
        when(asientoClientRest.getAsientosByAvionId(1L)).thenReturn(List.of(
                skyscraper.Flota.dto.AsientoDTO.builder().id(1L).fila("A1").disponible(true).clase(skyscraper.Flota.dto.ClaseDTO.builder().id(1L).nombre("Economy").build()).build(),
                skyscraper.Flota.dto.AsientoDTO.builder().id(2L).fila("A2").disponible(false).clase(skyscraper.Flota.dto.ClaseDTO.builder().id(2L).nombre("Business").build()).build(),
                skyscraper.Flota.dto.AsientoDTO.builder().id(3L).fila("A3").disponible(true).clase(skyscraper.Flota.dto.ClaseDTO.builder().id(1L).nombre("Economy").build()).build()
            ));
        List<AsientoDTO> asiento = avionService.getAsientosByClaseAvion("Economy", 1L);
        assert asiento != null;
        assert asiento.size() == 2;
    
    }

    @Test
    public void testGetAsientoByPosicionInAvion() {
        // probar que llame el asiento si existe un asiento con el id de avion y posicion
        when(asientoClientRest.getAsientosByAvionId(1L)).thenReturn(List.of(
            skyscraper.Flota.dto.AsientoDTO.builder().id(1L).fila("A1").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(2L).fila("A2").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(2L).nombre("Pasillo").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(3L).fila("A3").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build()
        ));
        when(asientoClientRest.getAsientosByPosicion("Ventana")).thenReturn(List.of(
            skyscraper.Flota.dto.AsientoDTO.builder().id(1L).fila("A1").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(3L).fila("A3").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build()
        ));
        List<AsientoDTO> asiento = avionService.getAsientoByPosicionInAvion("Ventana", 1L);
        assert asiento != null;
        assert asiento.size() == 2;
    }

    @Test
    public void testGetAsientoByPosicionInAvionSameAvion()
    //la funcion ignora asientos on otors id, por lo que si se le pasa un avionId que no tiene asientos con la posicion, devuelve una lista vacia
    {
        when(asientoClientRest.getAsientosByAvionId(1L)).thenReturn(List.of(
            skyscraper.Flota.dto.AsientoDTO.builder().id(1L).fila("A1").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(2L).fila("A2").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(2L).nombre("Pasillo").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(3L).fila("A3").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build()
        ));
        when(asientoClientRest.getAsientosByPosicion("Ventana")).thenReturn(List.of(
            skyscraper.Flota.dto.AsientoDTO.builder().id(4L).fila("B1").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(5L).fila("B2").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build()
        ));
        List<AsientoDTO> asiento = avionService.getAsientoByPosicionInAvion("Ventana", 1L);
        assert asiento != null;
        assert asiento.size() == 0;
    }

    @Test
    public void testGetAsientoByPosicionAndClase() {
        // probar que llame el asiento si existe un asiento con la clase y posicion
        when(asientoClientRest.getAsientosByClase("Economy")).thenReturn(List.of(
            skyscraper.Flota.dto.AsientoDTO.builder().id(1L).fila("A1").clase(skyscraper.Flota.dto.ClaseDTO.builder().id(1L).nombre("Economy").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(2L).fila("A2").clase(skyscraper.Flota.dto.ClaseDTO.builder().id(2L).nombre("Business").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(3L).fila("A3").clase(skyscraper.Flota.dto.ClaseDTO.builder().id(1L).nombre("Economy").build()).build()
        ));
        when(asientoClientRest.getAsientosByPosicion("Ventana")).thenReturn(List.of(
            skyscraper.Flota.dto.AsientoDTO.builder().id(1L).fila("A1").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(3L).fila("A3").posicion(skyscraper.Flota.dto.PosicionDTO.builder().id(1L).nombre("Ventana").build()).build()
        ));
        List<AsientoDTO> asiento = avionService.getAsientoByPosicionAndClase("Economy", "Ventana");
        assert asiento != null;
        assert asiento.size() == 2;
    }

    @Test
    public void testCambiarDisponibilidadAsiento() {
        // probar que llame el asiento si existe un asiento con el id de asiento y cambie su disponibilidad
        when(asientoClientRest.getAsientoById(1L)).thenReturn(skyscraper.Flota.dto.AsientoDTO.builder().id(1L).disponible(true).build());
        avionService.cambiarDisponibilidadAsiento(1L);
        verify(asientoClientRest, times(1)).ocuparAsiento(1L);
    }

    @Test
    public void testAsientosDisponiblesEnAvion()  {
        // probar que llame el asiento si existe un asiento con el id de avion y devuelva la cantidad de asientos disponibles
        when(asientoClientRest.getAsientosByAvionId(1L)).thenReturn(List.of(
            skyscraper.Flota.dto.AsientoDTO.builder().id(1L).disponible(true).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(2L).disponible(false).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(3L).disponible(true).build()
        ));
        when(avionRepository.findById(1L)).thenReturn(java.util.Optional.of(Avion.builder().id(1L).capacidad(5).build()));
        int asientosDisponibles = avionService.asientosDisponiblesEnAvion(1L);
        assert asientosDisponibles == 4;
    }

    @Test
    public void testAsientosDisponiblesEnAvionNoExiste()  {
        // probar que llame el asiento si existe un asiento con el id de avion y devuelva 0 si no existe el avion
        when(asientoClientRest.getAsientosByAvionId(1L)).thenReturn(List.of(
            skyscraper.Flota.dto.AsientoDTO.builder().id(1L).disponible(true).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(2L).disponible(false).build(),
            skyscraper.Flota.dto.AsientoDTO.builder().id(3L).disponible(true).build()
        ));
        when(avionRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        int asientosDisponibles = avionService.asientosDisponiblesEnAvion(1L);
        assert asientosDisponibles == 0;
    }

    @Test
    public void testCrearAvion() {
        // probar que creo el avion y lo agrego a la base de datos
        Avion avion = Avion.builder().id(1L).modelo("Boeing 737").capacidad(200).build();
        when(avionRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        boolean creado = avionService.crearAvion(avion);
        assert creado;
        verify(avionRepository, times(1)).save(avion);
    }

    @Test
    public void testCrearAvionYaExiste() {
        // probar que no creo el avion si ya existe en la base de datos
        Avion avion = Avion.builder().id(1L).modelo("Boeing 737").capacidad(200).build();
        when(avionRepository.findById(1L)).thenReturn(java.util.Optional.of(avion));
        boolean creado = avionService.crearAvion(avion);
        assert !creado;
        verify(avionRepository, times(0)).save(avion);
    }

}

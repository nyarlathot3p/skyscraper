package skyscraper.puestos.service;

import skyscraper.puestos.model.Asiento;
import skyscraper.puestos.repository.AsientosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import java.util.List;
@Service

public class AsientoService {
    @Autowired
    private AsientosRepository asientoRepository;
    //asientos por clase
    public List<Asiento> getAsientosByClase(String nombreClase) {
        return asientoRepository.findByClaseNombre(nombreClase);
    }
    //asientos por posición
    public List<Asiento> getAsientosByPosicion(String nombrePosicion) {
        return asientoRepository.findByPosicionNombre(nombrePosicion);
    }
    //asientos por fila
    public List<Asiento> getAsientosByFila(String fila) {
        return asientoRepository.findByFila(fila);
    }

    //dejar un asiento como ocupado
    public void ocuparAsiento(Long asientoId) {
        asientoRepository.ocuparAsiento(asientoId);
    }

    //desocupar asiento
    public void desocuparAsiento(Long asientoId) {
        asientoRepository.desocuparAsiento(asientoId);
    }

    //asientos de un avion
    public List<Asiento> getAsientosByAvionId(Long avionId) {
        return asientoRepository.findByAvionId(avionId);
    }

    //asientos de una clase y avion disponibles
    /* public List<Asiento> getAsientosByAvionIdAndClaseNombre(Long avionId, String nombreClase) {
        return asientoRepository.findByAvionIdAndClaseNombre(avionId, nombreClase);
    } */

    //asiento por su id
    public Asiento getAsientoById(Long asientoId) {
        return asientoRepository.findById(asientoId).orElse(null);
    }


}

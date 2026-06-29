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
    public List<Asiento> getAsientosByFila(int fila) {
        return asientoRepository.findByFila(fila);
    }

    //dejar un asiento como ocupado
    public boolean ocuparAsiento(Long asientoId) {
    if (getAsientoById(asientoId) != null) {
        asientoRepository.ocuparAsiento(asientoId);
        return true;
        }
        else
        {
        return  false;
        }
    }

    //desocupar asiento
    public boolean desocuparAsiento(Long asientoId) {
    if (getAsientoById(asientoId) != null) {
        asientoRepository.desocuparAsiento(asientoId);
        return true;
        }
        else
        {
        return  false;
        }
    }

    //asientos de un avion
    public List<Asiento> getAsientosByAvionId(Long avionId) {
        return asientoRepository.findByAvionId(avionId);
    }

    //asiento por su id
    public Asiento getAsientoById(Long asientoId) {
        return asientoRepository.findById(asientoId).orElse(null);
    }


}

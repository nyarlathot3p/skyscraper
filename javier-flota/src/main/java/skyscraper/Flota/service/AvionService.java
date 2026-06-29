package skyscraper.Flota.service;

import skyscraper.Flota.clients.AsientoClientRest;
import skyscraper.Flota.dto.AsientoDTO;
import skyscraper.Flota.model.Avion;
import skyscraper.Flota.repository.AvionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AvionService {
    @Autowired
    private AvionRepository avionRepository;

    public List<Avion> getAvionesByAerolineaId(Long aerolineaId) {
        return avionRepository.findAvionesByAerolineaId(aerolineaId);
    }

    @Autowired
    private AsientoClientRest asientoClientRest;

    public List<AsientoDTO> getAsientosByClaseAvion(String nombreClase, long avionId) {
        List<AsientoDTO> asientos = asientoClientRest.getAsientosByAvionId(avionId);
        List<AsientoDTO> asientosUtiles = new java.util.ArrayList<>();
        for (AsientoDTO asiento : asientos) {
            if (asiento.getClase().getNombre().equals(nombreClase)) {
                if (asiento.isDisponible()) {
                    asientosUtiles.add(asiento);
                }
            }
        }
        return asientosUtiles;
    }

    public List<AsientoDTO> getAsientoByPosicionInAvion(String nombrePosicion, long avionId) {
        List<AsientoDTO> asientosf = new java.util.ArrayList<>();
        List<AsientoDTO> asientosDeAvion = asientoClientRest.getAsientosByAvionId(avionId);
        List<AsientoDTO> asientosDePosicion = asientoClientRest.getAsientosByPosicion(nombrePosicion);
        for (AsientoDTO asientoA : asientosDeAvion) {
            for (AsientoDTO asientoP : asientosDePosicion) {
                if (asientoA.getId().equals(asientoP.getId())) {
                    asientosf.add(asientoA);
                }
            }
        }
        return asientosf;
    }

    public List<AsientoDTO> getAsientoByPosicionAndClase(String nombreClase, String nombrePosicion) {
        List<AsientoDTO> asientosf = new java.util.ArrayList<>();
        List<AsientoDTO> asientosDeClase = asientoClientRest.getAsientosByClase(nombreClase);
        List<AsientoDTO> asientosDePosicion = asientoClientRest.getAsientosByPosicion(nombrePosicion);
        for (AsientoDTO asientoA : asientosDeClase) {
            for (AsientoDTO asientoP : asientosDePosicion) {
                if (asientoA.getId().equals(asientoP.getId())) {
                    asientosf.add(asientoA);
                }
            }
        }
        return asientosf;
    }

    public void cambiarDisponibilidadAsiento(long asientoId) {
        AsientoDTO asiento = asientoClientRest.getAsientoById(asientoId);
        if (asiento.isDisponible()) {
            asientoClientRest.ocuparAsiento(asientoId);
        } else {
            asientoClientRest.desocuparAsiento(asientoId);
        }
    }

    public int asientosDisponiblesEnAvion(long avionId) {
        List<AsientoDTO> asientosUsados= asientoClientRest.getAsientosByAvionId(avionId);
        for (AsientoDTO asiento : asientosUsados) {
            if (asiento.isDisponible()) {
                asientosUsados.remove(asiento);
            }
        }
        Optional <Avion> avion = avionRepository.findById(avionId);
        if (avion.isPresent()) {
            return avion.get().getCapacidad() - asientosUsados.size();
        } else {
            return 0;
        }
    }

    public boolean crearAvion(Avion avion) {
        if (avionRepository.findById(avion.getId()) == null) {
            avionRepository.save(avion);
            return true;
        } else {
            return false;
        }
    }


}

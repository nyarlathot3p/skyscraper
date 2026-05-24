package skyscraper.puestos.controller;

import skyscraper.puestos.service.AsientoService;
import java.util.List;
import skyscraper.puestos.model.Asiento;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/puestoss")

public class PuestosController {
    @Autowired
    private AsientoService asientoService;

    @GetMapping("/asientos/clase/{nombreClase}")
    public List<Asiento> getAsientosByClase(@PathVariable String nombreClase) {
        return asientoService.getAsientosByClase(nombreClase);
    }

    @GetMapping("/asientos/posicion/{nombrePosicion}")
    public List<Asiento> getAsientosByPosicion(@PathVariable String nombrePosicion) {
        return asientoService.getAsientosByPosicion(nombrePosicion);
    }

    @GetMapping("/asientos/fila/{fila}")
    public List<Asiento> getAsientosByFila(@PathVariable String fila) {
        return asientoService.getAsientosByFila(fila);
    }

    @PostMapping("/asientos/ocupar")
    public void ocuparAsiento(@RequestBody long asientoId) {
        asientoService.ocuparAsiento(asientoId);
    }

    @PostMapping("/asientos/desocupar")
    public void desocuparAsiento(@RequestBody long asientoId) {
        asientoService.desocuparAsiento(asientoId);
    }

    @GetMapping("/asientos/avion/{avionId}")
    public List<Asiento> getAsientosByAvionId(@PathVariable Long avionId) {
        return asientoService.getAsientosByAvionId(avionId);
    }

    /* @GetMapping("/asientos/avion/{avionId}/clase/{nombreClase}")
    public List<Asiento> getAsientosByAvionIdAndClaseNombre(@PathVariable Long avionId, @PathVariable String nombreClase) {
        return asientoService.getAsientosByAvionIdAndClaseNombre(avionId, nombreClase);
    } */
    
    @GetMapping("/asientos/{asientoId}")
    public Asiento getAsientoById(@PathVariable Long asientoId) {
        return asientoService.getAsientoById(asientoId);
    }
    
    

}
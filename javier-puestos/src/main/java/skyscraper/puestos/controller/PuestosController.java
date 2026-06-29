package skyscraper.puestos.controller;

import skyscraper.puestos.service.AsientoService;
import java.util.List;
import skyscraper.puestos.model.Asiento;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

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
    @Operation(summary = "Obtener asientos por nombre de clase", description = "Devuelve una lista de asientos asociados a una clase específica")
    public List<Asiento> getAsientosByClase(@PathVariable String nombreClase) {
        return asientoService.getAsientosByClase(nombreClase);
    }

    @GetMapping("/asientos/posicion/{nombrePosicion}")
    @Operation(summary = "Obtener asientos por nombre de posición", description = "Devuelve una lista de asientos asociados a una posición específica")
    public List<Asiento> getAsientosByPosicion(@PathVariable String nombrePosicion) {
        return asientoService.getAsientosByPosicion(nombrePosicion);
    }

    @GetMapping("/asientos/fila/{fila}")
    @Operation(summary = "Obtener asientos por fila", description = "Devuelve una lista de asientos asociados a una fila específica")
    public List<Asiento> getAsientosByFila(@PathVariable int fila) {
        return asientoService.getAsientosByFila(fila);
    }

    @PostMapping("/asientos/ocupar")
    @Operation(summary = "Ocupar asiento", description = "Ocupa un asiento específico")
    public void ocuparAsiento(@RequestBody long asientoId) {
        asientoService.ocuparAsiento(asientoId);
    }

    @PostMapping("/asientos/desocupar")
    @Operation(summary = "Desocupar asiento", description = "Desocupa un asiento específico")
    public void desocuparAsiento(@RequestBody long asientoId) {
        asientoService.desocuparAsiento(asientoId);
    }

    @GetMapping("/asientos/avion/{avionId}")
    @Operation(summary = "Obtener asientos por ID de avión", description = "Devuelve una lista de asientos asociados a un avión específico")
    public List<Asiento> getAsientosByAvionId(@PathVariable Long avionId) {
        return asientoService.getAsientosByAvionId(avionId);
    }

    /* @GetMapping("/asientos/avion/{avionId}/clase/{nombreClase}")
    public List<Asiento> getAsientosByAvionIdAndClaseNombre(@PathVariable Long avionId, @PathVariable String nombreClase) {
        return asientoService.getAsientosByAvionIdAndClaseNombre(avionId, nombreClase);
    } */
    
    @GetMapping("/asientos/{asientoId}")
    @Operation(summary = "Obtener asiento por ID", description = "Devuelve un asiento específico por su ID")
    public Asiento getAsientoById(@PathVariable Long asientoId) {
        return asientoService.getAsientoById(asientoId);
    }
    
    

}
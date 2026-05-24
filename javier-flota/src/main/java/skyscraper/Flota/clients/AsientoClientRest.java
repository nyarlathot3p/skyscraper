package skyscraper.Flota.clients;

import skyscraper.Flota.dto.AsientoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import java.util.List;

@FeignClient(name = "puestos", url = "localhost:8085/api/v1/puestoss")
public interface AsientoClientRest {
    @GetMapping("/asientos/clase/{nombreClase}")
    List<AsientoDTO> getAsientosByClase(@PathVariable("nombreClase") String nombreClase);

    @GetMapping("/asientos/posicion/{nombrePosicion}")
    List<AsientoDTO> getAsientosByPosicion(@PathVariable("nombrePosicion") String nombrePosicion);
    
    @GetMapping("/asientos/fila/{fila}")
    List<AsientoDTO> getAsientosByFila(@PathVariable("fila") String fila);
    
    @PostMapping("/asientos/ocupar")
    public void ocuparAsiento(@RequestBody long asientoId);

    @PostMapping("/asientos/desocupar")
    public void desocuparAsiento(@RequestBody long asientoId);

    @GetMapping("/asientos/avion/{avionId}")
    public List<AsientoDTO> getAsientosByAvionId(@PathVariable("avionId") Long avionId);

    @GetMapping("/asientos/{asientoId}")
    public AsientoDTO getAsientoById(@PathVariable("asientoId") Long asientoId);

}



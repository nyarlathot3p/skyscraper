package skyscraper.sedes.client;

import skyscraper.sedes.dto.CiudadDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.annotations.OpenAPI30;

@FeignClient(name = "ciudad-service", url = "http://localhost:8083/api/v1/geaografia")
public interface CiudadClientRest {
    @GetMapping("/ciudad/{id}/nombre")
    @Operation(summary = "Obtener nombre de ciudad por ID", description = "Devuelve el nombre de una ciudad específica dado su ID")
    public String getNombreById(@PathVariable("id") Long id);

    @GetMapping("/ciudad/{nombre}/id")
    @Operation(summary = "Obtener ID de ciudad por nombre", description = "Devuelve el ID de una ciudad específica dado su nombre")
    public Long getIdByNombre(@PathVariable("nombre") String nombre);

    @GetMapping("/ciudades/region/{id}")
    @Operation(summary = "Obtener ciudades por ID de región", description = "Devuelve una lista de ciudades asociadas a una región específica dado su ID")
    public List<CiudadDTO> findByRegionId(@PathVariable("id") Long regionId);
    
    @GetMapping("/ciudades/region/nombre/{nombre}")
    @Operation(summary = "Obtener ciudades por nombre de región", description = "Devuelve una lista de ciudades asociadas a una región específica dado su nombre")
    public List<CiudadDTO> getCiudadesByRegionNombre(@PathVariable("nombre") String nombre);

}

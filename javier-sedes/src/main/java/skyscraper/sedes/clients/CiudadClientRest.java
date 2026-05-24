package skyscraper.sedes.clients;

import skyscraper.sedes.dto.CiudadDTO;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;

@FeignClient(name = "ciudad-service", url = "http://localhost:8083/api/v1/geaografia")
public interface CiudadClientRest {
    @GetMapping("/ciudad/{id}/nombre")
    public String getNombreById(@PathVariable("id") Long id);

    @GetMapping("/ciudad/{nombre}/id")
    public Long getIdByNombre(@PathVariable("nombre") String nombre);

    @GetMapping("/ciudades/region/{id}")
    public List<CiudadDTO> findByRegionId(@PathVariable("id") Long regionId);
    
    @GetMapping("/ciudades/region/nombre/{nombre}")
    public List<CiudadDTO> getCiudadesByRegionNombre(@PathVariable("nombre") String nombre);

}

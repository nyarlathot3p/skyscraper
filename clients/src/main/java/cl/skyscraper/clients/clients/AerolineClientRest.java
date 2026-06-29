package cl.skyscraper.clients.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.skyscraper.clients.dto.AerolineDTO;

@FeignClient(name="Flota", url="localhost:8082/api/v1/aerolinea")
public interface AerolineClientRest {

    @GetMapping
    List<AerolineDTO> getAll();

    @GetMapping("{/id}")
    AerolineDTO getAerolineById(@PathVariable("id") Long id);

} 

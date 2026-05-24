package cl.skyscraper.clients.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.skyscraper.clients.dto.AerolineDTO;

@FeignClient(name="ms-infra-aeroportuaria", url="localhost:8081/api/v1/aerolineas")
public interface AerolineClientRest {

    @GetMapping
    List<AerolineDTO> getAll();

    @GetMapping("{/id}")
    AerolineDTO getAerolineById(@PathVariable("id") Long id);

} 

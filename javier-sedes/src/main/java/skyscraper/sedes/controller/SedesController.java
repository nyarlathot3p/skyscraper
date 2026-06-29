package skyscraper.sedes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import skyscraper.sedes.client.CiudadClientRest;
import skyscraper.sedes.model.Aeropuerto;
import skyscraper.sedes.service.AeropuertoService;


@RestController
@RequestMapping("/api/sedes")
@Tag(name = "Sedes", description = "Operaciones relacionadas con sedes y aeropuertos")

public class SedesController {
    @Autowired
    private AeropuertoService aeropuertoService;
    @Autowired
    private CiudadClientRest ciudadClientRest;

    @GetMapping("/aeropuertos/ciudad/{nombre}")
    @Operation(summary = "Obtener aeropuertos por nombre de ciudad", description = "Devuelve una lista de aeropuertos asociados a una ciudad específica dado su nombre")
    public List<Aeropuerto> getAeropuertosByCiudadNombre(@PathVariable String nombre) {
        return aeropuertoService.getAeropuertosByCiudadNombre(nombre);
    }

    @GetMapping("/aeropuertos/region/{nombre}")
    @Operation(summary = "Obtener aeropuertos por nombre de región", description = "Devuelve una lista de aeropuertos asociados a una región específica dado su nombre")
    public List<Aeropuerto> getAeropuertosByRegionNombre(@PathVariable String nombre) {
        return aeropuertoService.getAeropuertosByRegionNombre(nombre);
    }

}

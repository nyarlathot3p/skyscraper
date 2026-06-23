package skyscraper.geaografia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skyscraper.geaografia.service.CiudadService;
import skyscraper.geaografia.service.RegionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import skyscraper.geaografia.model.Ciudad;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1/geaografia")
@Tag(name = "Geaografia", description = "Operaciones relacionadas con la geografía")

public class GeografiaController {
    @Autowired
    private CiudadService ciudadService;
    @Autowired
    private RegionService regionService;

    @GetMapping("/ciudad/{id}/nombre")
    @Operation(summary = "Obtener nombre de ciudad por ID")
    public String getNombreById(@PathVariable Long id) {
        return ciudadService.getNombreById(id);
    }
    @GetMapping("/ciudad/{nombre}/id")
    @Operation(summary = "Obtener ID de ciudad por nombre")
    public Long getIdByNombre(@PathVariable String nombre) {
        return ciudadService.getIdByNombre(nombre);
    }
    @GetMapping("/ciudades/region/{id}")
    @Operation(summary = "Obtener ciudades por ID de región")
    public List<Ciudad> getCiudadesByRegionId(@PathVariable Long Id) {
        return ciudadService.getCiudadesByRegionId(Id);
    }
    @GetMapping("/ciudades/region/nombre/{nombre}")
    @Operation(summary = "Obtener ciudades por nombre de región")
    public List<Ciudad> getCiudadesByRegionNombre(@PathVariable String nombre) {
        return ciudadService.getCiudadesByRegionNombre(nombre);
    }



}

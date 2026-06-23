package skyscraper.Flota.controller;


import skyscraper.Flota.service.VueloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import skyscraper.Flota.model.Avion;
import skyscraper.Flota.service.AvionService;
import skyscraper.Flota.repository.aerolineaReporsitory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import skyscraper.Flota.dto.AsientoDTO;
import skyscraper.Flota.clients.AsientoClientRest;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/flota")
@Tag(name = "Flota", description = "Operaciones relacionadas con la flota de aviones")

public class FlotaController {
    @Autowired
    private AvionService avionService;
    @Autowired
    private VueloService vueloService;
    @Autowired
    private aerolineaReporsitory aerolineaRepository;
    @Autowired
    private AsientoClientRest asientoClientRest;

    @GetMapping("/aviones")
    @Operation(summary = "Obtener aviones por ID de aerolínea", description = "Devuelve una lista de aviones asociados a una aerolínea específica")
    public List<Avion> getAvionesByAerolineaId(@RequestParam Long aerolineaId) {
        return avionService.getAvionesByAerolineaId(aerolineaId);
    }

    @GetMapping("/asientos/{id}")
    @Operation(summary = "Obtener asiento por ID", description = "Devuelve un asiento específico por su ID")
    public AsientoDTO getAsientoById(@PathVariable Long id) {
        return asientoClientRest.getAsientoById(id);
    }


    

}

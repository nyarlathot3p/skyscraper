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


@RestController
@RequestMapping("/api/flota")

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
    public List<Avion> getAvionesByAerolineaId(@RequestParam Long aerolineaId) {
        return avionService.getAvionesByAerolineaId(aerolineaId);
    }

    @GetMapping("/asientos/{id}")
    public AsientoDTO getAsientoById(@PathVariable Long id) {
        return asientoClientRest.getAsientoById(id);
    }


    

}

package com.example.MS_reservas.client;

import com.example.MS_reservas.model.dto.external.VueloDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-vuelos", url = "http://localhost:8091/api/vuelos")
public interface VueloClient {

    @GetMapping("/{id}")
    VueloDTO getVueloById(@PathVariable("id") Long id);
}

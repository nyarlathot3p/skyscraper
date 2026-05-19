package com.example.MS_reservas.client;

import com.example.MS_reservas.model.dto.external.AsientoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-flota", url = "http://localhost:8082/api/flota/asientos")
public interface FlotaClient {

    @GetMapping("/{id}")
    AsientoDTO getAsientoById(@PathVariable("id") Long id);
}

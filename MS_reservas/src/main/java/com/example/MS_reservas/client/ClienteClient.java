package com.example.MS_reservas.client;

import com.example.MS_reservas.model.dto.external.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-clientes", url = "http://localhost:9090/api/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    ClienteDTO getClienteById(@PathVariable("id") Long id);
}

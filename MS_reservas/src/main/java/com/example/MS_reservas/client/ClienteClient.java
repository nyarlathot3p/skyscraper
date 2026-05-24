package com.example.MS_reservas.client;

import com.example.MS_reservas.model.dto.external.ClienteDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MS-clientes", url = "${app.clients.clientes-url}/api/clientes")
public interface ClienteClient {

    @GetMapping("/{id}")
    ClienteDTO getClienteById(@PathVariable("id") Long id);
}

package com.example.MS_reservas.client;

import com.example.MS_reservas.model.dto.external.PagoRequestDTO;
import com.example.MS_reservas.model.dto.external.PagoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "MS-pagos", url = "${app.clients.pagos-url}/api/pagos")
public interface PagoClient {

    @PostMapping("/procesar")
    PagoResponseDTO procesarPago(@RequestBody PagoRequestDTO request);
}

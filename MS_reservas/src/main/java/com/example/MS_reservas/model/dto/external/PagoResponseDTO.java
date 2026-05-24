package com.example.MS_reservas.model.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {
    private Long idTransaccion;
    private String estado; 
    private String fechaTransaccion;

    public Long getIdTransaccion() {
        return idTransaccion;
    }

    public String getEstado() {
        return estado;
    }

    public String getFechaTransaccion() {
        return fechaTransaccion;
    }
}

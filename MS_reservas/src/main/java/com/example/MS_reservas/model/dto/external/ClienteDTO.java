package com.example.MS_reservas.model.dto.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String run;
    private String pnombre;
    private String email;
    // Agrega más campos si los necesitas, para la validación con el id basta.
}

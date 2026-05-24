package com.example.MS_reservas.model.dto.external;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    @NotNull(message = "El id del cliente es obligatorio")
    private Long id;

    @NotBlank(message = "El RUN no puede estar vacío")
    private String run;

    @NotBlank(message = "El primer nombre no puede estar vacío")
    private String pnombre;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe tener un formato válido")
    private String email;
   
}

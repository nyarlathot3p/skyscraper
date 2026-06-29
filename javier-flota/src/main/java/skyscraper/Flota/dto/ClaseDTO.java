package skyscraper.Flota.dto;

import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
@Builder
public class ClaseDTO {
    @NotNull(message = "El id no puede ser nulo")
    private Long id;
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;
}

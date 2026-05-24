package skyscraper.Flota.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Data
public class AsientoDTO {
    @NotNull(message = "El id no puede ser nulo")
    private Long id;
    
    @NotBlank(message = "La fila no puede estar vacía")
    private String fila;
    
    private int numero;
    
    private boolean disponible;
    
    @NotNull(message = "La clase no puede ser nula")
    private ClaseDTO clase;
    
    @NotNull(message = "La posición no puede ser nula")
    private PosicionDTO posicion;
}

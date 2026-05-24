package cl.skyscraper.clients.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

public record ReviewRequestDTO(@Size(min = 1, max = 4000, message = "Descripción debe tener entre 1 y 4000 caracteres")
    String description,

    @Min(value = 1, message = "El mínimo de estrellas es 1")
    @Max(value = 5, message = "El máximo de estrellas es 5")
    int stars
) {
}

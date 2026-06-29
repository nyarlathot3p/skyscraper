package cl.skyscraper.clients.dto;

import lombok.Data;

@Data
public class ReviewResponseDTO {
    private Long id;
    private String description;
    private int stars;
    private UserResponseDTO user; 
    private Long idAeroline;
    private AerolineDTO aerolinea; // Aquí inyectamos el objeto completo
}

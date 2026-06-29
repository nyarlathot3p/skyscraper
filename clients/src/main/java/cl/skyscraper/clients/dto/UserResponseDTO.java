package cl.skyscraper.clients.dto;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Long id;
    private int run;
    private String name;
    private String email;
    
}

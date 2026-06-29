package cl.skyscraper.clients.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class AerolineDTO {
    private Long id;
    
    @JsonProperty("nombre")
    private String name;
}

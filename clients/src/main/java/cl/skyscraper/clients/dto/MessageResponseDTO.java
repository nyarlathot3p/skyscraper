package cl.skyscraper.clients.dto;

import lombok.Builder;

@Builder
public record MessageResponseDTO(
    String message
) {}

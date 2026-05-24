package cl.skyscraper.clients.dto;

import cl.skyscraper.clients.util.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginRequestDTO(
    @NotBlank(message = Messages.EMAIL_CANNOT_BE_BLANK)
    @Email(message = Messages.EMAIL_MUST_BE_VALID)
    @Size(min = 1, max = 100, message = Messages.EMAIL_LENGTH)
    String email,

    @NotBlank(message = Messages.PASSWORD_CANNOT_BE_BLANK)
    @Size(min = 6, message = Messages.PASSWORD_MINIMUM_LENGTH)
    String password
) {

}

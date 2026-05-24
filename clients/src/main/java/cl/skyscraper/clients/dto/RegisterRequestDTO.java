package cl.skyscraper.clients.dto;

import java.time.LocalDate;
import cl.skyscraper.clients.util.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RegisterRequestDTO(
    @NotNull(message = Messages.RUN_CANNOT_BE_NULL)
    Integer run,

    @NotBlank(message = Messages.DV_CANNOT_BE_BLANK)
    @Size(min = 1, max = 1, message = Messages.DV_MUST_BE_ONE_CHARACTER)
    String dv,

    @NotBlank(message = Messages.USERNAME_CANNOT_BE_BLANK)
    @Size(min = 3, max = 50, message = Messages.USERNAME_LENGTH)
    String username,

    @NotBlank(message = Messages.NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.NAME_LENGTH)
    String name,

    @Size(max = 50, message = Messages.MIDDLE_NAME_LENGTH)
    String middlename,

    @NotBlank(message = Messages.LAST_NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.LAST_NAME_LENGTH)
    String lastname,

    @NotBlank(message = Messages.SECOND_LAST_NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.SECOND_LAST_NAME_LENGTH)
    String secondlastname,

    @NotNull(message = Messages.BIRTH_DATE_CANNOT_BE_NULL)
    LocalDate birthdate,

    @NotBlank(message = Messages.PHONE_NUMBER_CANNOT_BE_BLANK)
    String phonenumber,

    @NotBlank(message = Messages.EMAIL_CANNOT_BE_BLANK)
    @Email(message = Messages.EMAIL_MUST_BE_VALID)
    @Size(min = 1, max = 100, message = Messages.EMAIL_LENGTH)
    String email,

    @NotBlank(message = Messages.ADDRESS_CANNOT_BE_BLANK)
    @Size(min = 1, max = 100, message = Messages.ADDRESS_LENGTH)
    String address,

    @NotBlank(message = Messages.PASSWORD_CANNOT_BE_BLANK)
    @Size(min = 6, message = Messages.PASSWORD_MINIMUM_LENGTH)
    String password
) {
}
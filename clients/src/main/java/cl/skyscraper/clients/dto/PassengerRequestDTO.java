package cl.skyscraper.clients.dto;

import java.time.LocalDate;

import cl.skyscraper.clients.util.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerRequestDTO {

    @Min(value = 1000000, message = "Run debe tener por lo menos 7 digitos")
    @Max(value = 99999999, message = "Run debe tener un máximo de 8 digitos")
    private Integer run;

    @Size(min = 1, max = 1, message = Messages.DV_MUST_BE_ONE_CHARACTER)
    private String dv;

    @Size(min = 1, max = 50, message = Messages.FIRST_NAME_LENGTH)
    private String firstName;

    @Size(min = 1, max = 50, message = Messages.SECOND_NAME_LENGTH)
    private String secondName;

    @Size(min = 1, max = 50, message = Messages.PATERNAL_LAST_NAME_LENGTH)
    private String paternalLastName;

    @Size(min = 1, max = 50, message = Messages.MATERNAL_LAST_NAME_LENGTH)
    private String maternalLastName;

    private LocalDate birthDate;

    private String phone;

    @Email(message = Messages.EMAIL_MUST_BE_VALID)
    @Size(min = 1, max = 100, message = Messages.EMAIL_LENGTH)
    private String email;

}

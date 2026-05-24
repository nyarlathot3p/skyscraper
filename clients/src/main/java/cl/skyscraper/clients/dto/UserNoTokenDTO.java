package cl.skyscraper.clients.dto;

import java.time.LocalDate;

import cl.skyscraper.clients.util.Messages;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserNoTokenDTO {
    private Long id;

    private Integer run;

    @Size(min = 1, max = 1, message = Messages.DV_MUST_BE_ONE_CHARACTER)
    private String dv;

    @Size(min = 3, max = 50, message = Messages.USERNAME_LENGTH)
    private String username;

    @Size(min = 1, max = 50, message = Messages.NAME_LENGTH)
    private String name;

    @Size(max = 50, message = Messages.MIDDLE_NAME_LENGTH)
    private String middlename;

    @Size(min = 1, max = 50, message = Messages.LAST_NAME_LENGTH)
    private String lastname;

    @Size(min = 1, max = 50, message = Messages.SECOND_LAST_NAME_LENGTH)
    private String secondlastname;

    @Email(message = Messages.EMAIL_MUST_BE_VALID)
    @Size(min = 1, max = 100, message = Messages.EMAIL_LENGTH)
    private String email;

    private LocalDate birthdate;
    private String phonenumber;

    @Size(min = 1, max = 100, message = Messages.ADDRESS_LENGTH)
    private String address;
    
}
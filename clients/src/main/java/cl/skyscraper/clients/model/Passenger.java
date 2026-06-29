package cl.skyscraper.clients.model;

import java.time.LocalDate;

import cl.skyscraper.clients.util.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "passenger")
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = Messages.RUN_CANNOT_BE_NULL)
    @Min(value = 1000000, message = "Run debe tener por lo menos 7 digitos")
    @Max(value = 99999999, message = "Run debe tener un máximo de 8 digitos")
    private Integer run;

    @Column(nullable = false, length = 1)
    @NotBlank(message = Messages.DV_CANNOT_BE_BLANK)
    @Size(min = 1, max = 1, message = Messages.DV_MUST_BE_ONE_CHARACTER)
    private String dv;

    @Column(nullable = false, length = 50)
    @Size(min = 1, max = 50, message = Messages.FIRST_NAME_LENGTH)
    private String firstName;

    @Column(nullable = false, length = 50)
    @NotBlank(message = Messages.SECOND_NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.SECOND_NAME_LENGTH)
    private String secondName;

    @Column(nullable = false, length = 50)
    @NotBlank(message = Messages.PATERNAL_LAST_NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.PATERNAL_LAST_NAME_LENGTH)
    private String paternalLastName;

    @Column(nullable = false, length = 50)
    @NotBlank(message = Messages.MATERNAL_LAST_NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.MATERNAL_LAST_NAME_LENGTH)
    private String maternalLastName;

    @Column(nullable = false)
    @NotNull(message = Messages.BIRTH_DATE_CANNOT_BE_NULL)
    private LocalDate birthDate;

    @Column(nullable = false, length = 20)
    @NotBlank(message = Messages.PHONE_NUMBER_CANNOT_BE_BLANK)
    private String phone;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = Messages.EMAIL_CANNOT_BE_BLANK)
    @Email(message = Messages.EMAIL_MUST_BE_VALID)
    @Size(min = 1, max = 100, message = Messages.EMAIL_LENGTH)
    private String email;

}

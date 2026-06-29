package cl.skyscraper.clients.model;

import java.time.LocalDate;
import java.util.List;

import cl.skyscraper.clients.util.Messages;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
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
@Table(name = "user")

public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 8, nullable = false)
    @NotNull(message = Messages.RUN_CANNOT_BE_NULL)
    private Integer run;

    @Column(nullable = false)
    @NotBlank(message = Messages.DV_CANNOT_BE_BLANK)
    @Size(min = 1, max = 1, message = Messages.DV_MUST_BE_ONE_CHARACTER)
    private String dv;

    @Column(unique = true, nullable = false)
    @NotBlank(message = Messages.USERNAME_CANNOT_BE_BLANK)
    @Size(min = 3, max = 50, message = Messages.USERNAME_LENGTH)
    private String username;

    @Column(nullable = false)
    @NotBlank(message = Messages.NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.NAME_LENGTH)
    private String name;

    @Size(max = 50, message = Messages.MIDDLE_NAME_LENGTH)
    private String middlename;

    @Column(nullable = false)
    @NotBlank(message = Messages.LAST_NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.LAST_NAME_LENGTH)
    private String lastname;

    @Column(nullable = false)
    @NotBlank(message = Messages.SECOND_LAST_NAME_CANNOT_BE_BLANK)
    @Size(min = 1, max = 50, message = Messages.SECOND_LAST_NAME_LENGTH)
    private String secondlastname;

    @Column(nullable = false)
    @NotNull(message = Messages.BIRTH_DATE_CANNOT_BE_NULL)
    private LocalDate birthdate;

    @Column(nullable = false)
    @NotBlank(message = Messages.PHONE_NUMBER_CANNOT_BE_BLANK)
    private String phonenumber;

    @Column(unique = true, nullable = false)
    @NotBlank(message = Messages.EMAIL_CANNOT_BE_BLANK)
    @Email(message = Messages.EMAIL_MUST_BE_VALID)
    @Size(min = 1, max = 100, message = Messages.EMAIL_LENGTH)
    private String email;

    @Column(nullable = false)
    @NotBlank(message = Messages.ADDRESS_CANNOT_BE_BLANK)
    @Size(min = 1, max = 100, message = Messages.ADDRESS_LENGTH)
    private String address;

    @Column(nullable = false)
    @NotBlank(message = Messages.PASSWORD_CANNOT_BE_BLANK)
    @Size(min = 6, message = Messages.PASSWORD_MINIMUM_LENGTH)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Review> reviews;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Token> tokens;
    
}

package cl.skyscraper.clients.model;


import cl.skyscraper.clients.util.Messages;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tokens")
public class Token {

    public enum TokenType { 
        BEARER
    }

    @Id
    @GeneratedValue
    public Long id;

    @Column(unique = true)
    @NotBlank(message = Messages.TOKEN_CANNOT_BE_BLANK)
    public String token;

    @Enumerated(EnumType.STRING)
    @NotNull(message = Messages.TOKEN_TYPE_CANNOT_BE_NULL)
    @Default
    public TokenType tokenType = TokenType.BEARER;

    public boolean revoked;

    public boolean expired;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull(message = "Usuario no puede ser nulo")
    public User user;

}

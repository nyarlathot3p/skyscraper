package cl.skyscraper.clients.model;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "review")
public class Review {   

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name="id_usuario")
    @ManyToOne
    private User user;

    @NotNull(message = "Aerolinea ID no puede ser null")
    @Min(value = 1, message = "Aerolinea ID debe ser positivo")
    private Long idAeroline;

    @Column(length = 4000)
    @Size(min = 1, max = 4000, message = "Descripción debe tener entre 1 y 40000 caracteres")
    private String description;
 
    @Column(nullable=false)
    private Date date;

    @NotNull(message = "Las estrellas no pueden ser null")
    @Min(value = 1, message = "El mínimo de estrellas es 1")
    @Max(value = 5, message = "El máximo de estrellas es 5")
    private int stars;
}

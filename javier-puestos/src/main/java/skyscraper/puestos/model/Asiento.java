package skyscraper.puestos.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "asiento")
public class Asiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idAvion;
    private int fila;
    private String letra;
    private boolean disponible;

    @ManyToOne
    @JoinColumn(name = "clase_id")
    @JsonIgnoreProperties("asientos")
    private Clase clase;

    @ManyToOne
    @JoinColumn(name = "posicion_id")
    private Posicion posicion;

}

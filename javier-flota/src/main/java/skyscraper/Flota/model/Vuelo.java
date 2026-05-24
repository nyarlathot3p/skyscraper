package skyscraper.Flota.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "vuelo")
public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origen;
    private String destino;
    private String fecha;
    private String horaSalida;
    private String duracionMinutos;
    private String estado;

    @ManyToOne
    @JoinColumn(name = "aerolinea_id")
    @JsonIgnoreProperties("vuelos")
    private Aerolinea aerolinea;

    @ManyToOne
    @JoinColumn(name = "avion_id")
    @JsonIgnoreProperties("vuelos")
    private Avion IdAvion;
    

}

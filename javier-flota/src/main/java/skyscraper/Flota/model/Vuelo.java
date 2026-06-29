package skyscraper.Flota.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "vuelo")
@Builder


public class Vuelo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origen;
    private String destino;
    private Date fecha;
    private Time horaSalida;
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

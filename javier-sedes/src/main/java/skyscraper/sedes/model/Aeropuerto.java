package skyscraper.sedes.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Table(name = "aeropuerto")
@Builder

public class Aeropuerto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @OneToOne
    @JoinColumn(name = "origen_id")
    private Origen origen;
    @OneToOne
    @JoinColumn(name = "destino_id")
    private Destino destino;
    private long ciudadId;
    
}

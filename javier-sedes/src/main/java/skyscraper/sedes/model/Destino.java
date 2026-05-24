package skyscraper.sedes.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "destino")

public class Destino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @OneToOne(mappedBy = "destino")
    private Aeropuerto aeropuerto;



}

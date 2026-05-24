package skyscraper.sedes.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "origene")

public class Origen {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    @OneToOne(mappedBy = "origen")
    private Aeropuerto aeropuerto;

    

}

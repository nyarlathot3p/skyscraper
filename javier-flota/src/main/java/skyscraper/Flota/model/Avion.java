package skyscraper.Flota.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "avion")

public class Avion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String modelo;
    private int capacidad;
    private int filas;
    private int columnas;
    @OneToMany(mappedBy = "avion",cascade = CascadeType.ALL)
    private List<Vuelo> vuelos;
    

}

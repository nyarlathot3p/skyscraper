package skyscraper.puestos.model;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;
@Data
@Entity
@Table(name = "clase")

public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "clase", cascade = CascadeType.ALL)
    private List<Asiento> asientos;
}

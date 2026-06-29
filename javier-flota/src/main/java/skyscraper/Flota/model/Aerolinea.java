package skyscraper.Flota.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Entity
@Table(name = "aerolinea")
@Builder

public class Aerolinea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "aerolinea",cascade = CascadeType.ALL)
    private List<Vuelo> vuelos;
}

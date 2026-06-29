package skyscraper.puestos.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.util.List;
@Data
@Entity
@Table(name = "posicion")
@Builder
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "posicion", cascade = CascadeType.ALL)
    private List<Asiento> asientos;
}

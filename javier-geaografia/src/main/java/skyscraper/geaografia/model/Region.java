package skyscraper.geaografia.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder

@Table(name = "regiones")

public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "region", cascade = CascadeType.ALL)
    private java.util.List<Ciudad> ciudades;

}

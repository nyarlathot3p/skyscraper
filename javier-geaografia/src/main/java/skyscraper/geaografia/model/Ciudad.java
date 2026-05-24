package skyscraper.geaografia.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "ciudades")
public class Ciudad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name = "region_id")
    private Region region;

}

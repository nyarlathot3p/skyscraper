package skyscraper.puestos.repository;

import skyscraper.puestos.model.Posicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PosicionRepository extends JpaRepository<Posicion, Long> {

}

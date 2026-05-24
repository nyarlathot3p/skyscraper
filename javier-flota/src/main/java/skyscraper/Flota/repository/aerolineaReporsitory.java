package skyscraper.Flota.repository;

import skyscraper.Flota.model.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface aerolineaReporsitory extends JpaRepository<Aerolinea, Long> {

}

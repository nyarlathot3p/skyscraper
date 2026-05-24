package skyscraper.Flota.repository;

import skyscraper.Flota.model.Avion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

@Repository

public interface AvionRepository extends JpaRepository<Avion, Long> {
    //filtraR AVIONES POR AEROLINEA
    @Query("SELECT a FROM Avion a JOIN a.vuelos v JOIN v.aerolinea al WHERE al.id = :aerolineaId")
    List<Avion> findAvionesByAerolineaId(Long aerolineaId);

    //capasidad de un avion por su id
    @Query("SELECT a.capacidad FROM Avion a WHERE a.id = :avionId")
    int findCapacidadByAvionId(Long avionId);

    @Query("SELECT a FROM Avion a WHERE a.id = :id")
    Optional<Avion> findById(Long id);
}

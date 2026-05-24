package skyscraper.sedes.repository;

import java.util.List;
import skyscraper.sedes.model.Aeropuerto;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Long> {

    //lista de aeropuertos con un id de destino especifico
    @Query("SELECT a FROM Aeropuerto a WHERE a.destino.id = :destinoId")
    List<Aeropuerto> findByDestinoId(Long destinoId);

    //lista de aeropuertos en una ciudad especifica
    @Query("SELECT a FROM Aeropuerto a WHERE a.ciudadId = :ciudadId")
    List<Aeropuerto> findByCiudadId(Long ciudadId);

    



    

    

}

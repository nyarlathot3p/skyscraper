package skyscraper.geaografia.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skyscraper.geaografia.model.Ciudad;


@Repository

public interface CiudadRepository extends JpaRepository<Ciudad, Long> {

    //conseguir nombre de ciudad por id
    @Query("SELECT c.nombre FROM Ciudad c WHERE c.id = :id")
    String findNombreById(Long id);

    //conseguir id de ciudad por nombre
    @Query("SELECT c.id FROM Ciudad c WHERE c.nombre = :nombre")
    Long findIdByNombre(String nombre);

    //ciudades en una region por id de region
    @Query("SELECT c FROM Ciudad c WHERE c.region.id = :regionId")
    java.util.List<Ciudad> findByRegionId(Long regionId);

    //ciudades en una region por nombre de region
    @Query("SELECT c FROM Ciudad c WHERE c.region.nombre = :regionNombre")
    java.util.List<Ciudad> findByRegionNombre(String regionNombre);
    

    



}

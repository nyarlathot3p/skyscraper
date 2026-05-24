package skyscraper.puestos.repository;

import skyscraper.puestos.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
@Repository

public interface AsientosRepository extends JpaRepository<Asiento, Long> {
    //asientos por clase
    @Query("SELECT a FROM Asiento a WHERE a.clase.nombre = :nombreClase")
    List<Asiento> findByClaseNombre(String nombreClase);
    
    //asientos por posición
    @Query("SELECT a FROM Asiento a WHERE a.posicion.nombre = :nombrePosicion")
    List<Asiento> findByPosicionNombre(String nombrePosicion);
    
    //asientos por fila
    @Query("SELECT a FROM Asiento a WHERE a.fila = :fila")
    List<Asiento> findByFila(String fila);

    //dejar un asiento como ocupado
    @Query("UPDATE Asiento a SET a.ocupado = true WHERE a.id = :asientoId")
    void ocuparAsiento(Long asientoId);

    //desocupar asiento
    @Query("UPDATE Asiento a SET a.ocupado = false WHERE a.id = :asientoId")
    void desocuparAsiento(Long asientoId);

    //asientos de un avion
    @Query("SELECT a FROM Asiento a WHERE a.avion.id = :avionId")
    List<Asiento> findByAvionId(Long avionId);

    /* @Query("SELECT a FROM Asiento a WHERE a.avion.id = :avionId AND a.disponible = true AND a.clase.nombre = :nombreClase")
    List<Asiento> findByAvionIdAndClaseNombre(Long avionId, String nombreClase); */

    //datos de un asiento por su id
    @Query("SELECT a FROM Asiento a WHERE a.id = :id")
    Optional<Asiento> findById(Long id);


}

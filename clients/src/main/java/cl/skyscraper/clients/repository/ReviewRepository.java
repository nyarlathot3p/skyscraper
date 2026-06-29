package cl.skyscraper.clients.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cl.skyscraper.clients.model.Review;
import feign.Param;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long>{
    List<Review> findAll();
    
    List<Review> findByIdAeroline(Long idAeroline);

    @Query("SELECT r FROM Review r WHERE MONTH(r.date) = :month")
    List<Review> findByMonth(@Param("month") Integer month);

    List<Review> findByIdAerolineOrderByDateDesc(Long idAeroline);

    List<Review> findByUserId(Long userId);
    
    List<Review> findByStars(int stars);

    List<Review> findAllByOrderByDateDesc();

    List<Review> findByIdAerolineAndStarsOrderByDateDesc(Long idAeroline, int stars);

}

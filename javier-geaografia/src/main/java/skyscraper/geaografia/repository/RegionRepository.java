package skyscraper.geaografia.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import skyscraper.geaografia.model.Region;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    

}

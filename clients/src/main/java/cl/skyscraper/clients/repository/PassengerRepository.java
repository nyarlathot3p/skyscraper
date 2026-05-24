package cl.skyscraper.clients.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.skyscraper.clients.model.Passenger;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    Optional<Passenger> findByRun(Integer run);

    Optional<Passenger> findByEmail(String email);

    List<Passenger> findAll();

    long count();

}

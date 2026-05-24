package cl.skyscraper.clients.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.skyscraper.clients.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    // Usado para autenticar
    Optional<User> findByEmail(String email);
    
    Optional<User> findById(Long id);

    Optional<User> findByRun(Integer run);

    Optional<User> findByUsername(String usernam);

    List<User> findAll();

} 

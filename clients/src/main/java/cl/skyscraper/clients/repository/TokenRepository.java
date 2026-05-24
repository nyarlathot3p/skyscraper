package cl.skyscraper.clients.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.skyscraper.clients.model.Token;

public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findAllValidIsFalseOrRevokedIsFalseByUserId(Long id);
    Optional<Token> findByToken(String jwtToken);
} 

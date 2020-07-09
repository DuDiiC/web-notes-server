package pl.maciejdudek.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.maciejdudek.project.model.RegisterVerificationToken;

import java.util.Optional;

@Repository
public interface RegisterTokenRepository extends JpaRepository<RegisterVerificationToken, Long> {
    Optional<RegisterVerificationToken> findByToken(String token);
}

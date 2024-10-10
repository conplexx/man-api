package web2.man.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web2.man.enums.UserRole;
import web2.man.models.entities.AuthToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AuthTokenRepository extends JpaRepository<AuthToken, UUID> {
    boolean existsById(UUID id);
    Optional<AuthToken> findById(UUID id);
    Optional<AuthToken> findByToken(String token);
    boolean existsByToken(String token);
    void deleteByUserIdAndUserRole(UUID userId, UserRole userRole);
}

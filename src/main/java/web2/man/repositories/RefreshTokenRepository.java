package web2.man.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web2.man.models.entities.RefreshToken;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {
    boolean existsById(UUID id);
    Optional<RefreshToken> findById(UUID id);
    Optional<RefreshToken> findByToken(String token);
    void deleteByUserId(UUID userId);
}

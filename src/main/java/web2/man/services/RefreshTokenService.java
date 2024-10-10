package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.enums.UserRole;
import web2.man.models.entities.RefreshToken;
import web2.man.repositories.RefreshTokenRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Autowired
    final RefreshTokenRepository refreshTokenRepository;
    @Transactional
    public RefreshToken save(RefreshToken token) {
        return refreshTokenRepository.save(token);
    }
    @Transactional
    public void deleteByUserIdAndUserRole(UUID userId, UserRole userRole) {
        refreshTokenRepository.deleteByUserIdAndUserRole(userId, userRole);
    }
    public Optional<RefreshToken> findById(UUID id) {
        return refreshTokenRepository.findById(id);
    }
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }
}
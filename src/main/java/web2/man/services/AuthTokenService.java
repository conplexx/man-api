package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.models.entities.AuthToken;
import web2.man.repositories.AuthTokenRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthTokenService {
    @Autowired
    final AuthTokenRepository authTokenRepository;
    @Transactional
    public AuthToken save(AuthToken token) {
        return authTokenRepository.save(token);
    }
    @Transactional
    public void deleteByUserId(UUID userId) {
        authTokenRepository.deleteByUserId(userId);
    }
    public Optional<AuthToken> findById(UUID id) {
        return authTokenRepository.findById(id);
    }
    public Optional<AuthToken> findByToken(String token) {
        return authTokenRepository.findByToken(token);
    }
}
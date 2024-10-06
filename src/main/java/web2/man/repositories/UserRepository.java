package web2.man.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web2.man.models.entities.User;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);
    boolean existsByCpf(String cpf);
    boolean existsById(UUID id);
    boolean existsByEmail(String email);
    Optional<User> findByCpf(String cpf);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    void deleteById(UUID id);
    void deleteByCpf(String cpf);
}

package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.models.User;
import web2.man.repositories.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class UserService {
    @Autowired
    UserRepository userRepository;

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }
    @Transactional
    public void deleteById(UUID id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public void deleteByCpf(String cpf){
        userRepository.deleteByCpf(cpf);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public Optional<User> findById(UUID id) {
        return userRepository.findById(id);
    }
    public Optional<User> findByCpf(String cpf) {
        return userRepository.findByCpf(cpf);
    }
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByCpf(String cpf) {
        return userRepository.existsByCpf(cpf);
    }
    public boolean existsById(UUID id) {
        return userRepository.existsById(id);
    }
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }




}

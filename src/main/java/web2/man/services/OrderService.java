package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.enums.ClientOrderState;
import web2.man.models.entities.Order;
import web2.man.repositories.OrderRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Transactional
    public Order save(Order user) {
        return orderRepository.save(user);
    }
    @Transactional
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
    public List<Order> findAllWithOpenState() {
        return orderRepository.findAllByState(ClientOrderState.OPEN);
    }
    public List<Order> findAllByState(ClientOrderState state) {
        return orderRepository.findAllByState(state);
    }
    public List<Order> findAllByClientId(UUID id) {
        return orderRepository.findAllByClientId(id);
    }
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }
    public boolean existsById(UUID id) {
        return orderRepository.existsById(id);
    }

}

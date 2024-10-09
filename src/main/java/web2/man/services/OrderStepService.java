package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.models.entities.Order;
import web2.man.models.entities.OrderStep;
import web2.man.repositories.OrderRepository;
import web2.man.repositories.OrderStepRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderStepService {
    @Autowired
    OrderStepRepository orderStepRepository;

    @Transactional
    public OrderStep save(OrderStep orderStep) {
        return orderStepRepository.save(orderStep);
    }
    @Transactional
    public void deleteById(UUID id) {
        orderStepRepository.deleteById(id);
    }
    public List<OrderStep> findAll() {
        return orderStepRepository.findAll();
    }
    public List<OrderStep> findAllByOrderId(UUID id) {
        return orderStepRepository.findAllByOrderId(id);
    }
    public Optional<OrderStep> findById(UUID id) {
        return orderStepRepository.findById(id);
    }
}

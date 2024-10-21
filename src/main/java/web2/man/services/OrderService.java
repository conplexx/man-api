package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.enums.OrderState;
import web2.man.models.entities.Order;
import web2.man.models.entities.OrderStep;
import web2.man.repositories.OrderRepository;

import java.util.*;

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
        return orderRepository.findAllByState(OrderState.OPEN);
    }
    public List<Order> findAllByState(OrderState state) {
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
    public List<Order> findAllOrdersWithEmployeeId(UUID id) { return orderRepository.findAllOrdersWithEmployeeId(id); }
    public boolean isEmployeeResponsibleForOrder(UUID orderId, UUID employeeId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            List<OrderStep> steps = order.getSteps();
            if (!steps.isEmpty()) {
                OrderStep lastStep = steps.get(steps.size() - 1);
                return lastStep.getEmployeeId().equals(employeeId);
            }
        }
        return false;
    }
    public List<Order> findEmployeeOrdersWithinLast24Hours(UUID employeeId) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, -24);
        Date last24Hours = calendar.getTime();
        return orderRepository.findEmployeeOrdersAfterDate(employeeId, last24Hours);
    }

    public List<Order> findEmployeeOrdersInDateRange(UUID employeeId, Date startDate, Date endDate) {
        return orderRepository.findEmployeeOrdersInDateRange(employeeId, startDate, endDate);
    }



}




























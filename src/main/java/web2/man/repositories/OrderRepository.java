package web2.man.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web2.man.enums.ClientOrderState;
import web2.man.models.entities.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order save(Order order);
    boolean existsById(UUID id);
    Optional<Order> findById(UUID id);
    List<Order> findAll();
    List<Order> findAllByState(ClientOrderState state);
    List<Order> findAllByClientId(UUID id);
    void deleteById(UUID id);
}

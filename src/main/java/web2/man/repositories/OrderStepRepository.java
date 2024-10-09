package web2.man.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import web2.man.models.entities.Order;
import web2.man.models.entities.OrderStep;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderStepRepository extends JpaRepository<OrderStep, UUID> {
    List<OrderStep> findAllByOrderId(UUID id);
}

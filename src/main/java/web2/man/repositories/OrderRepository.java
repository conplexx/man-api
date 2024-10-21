package web2.man.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import web2.man.enums.OrderState;
import web2.man.models.entities.Order;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    Order save(Order order);
    boolean existsById(UUID id);
    Optional<Order> findById(UUID id);
    List<Order> findAll();
    List<Order> findAllByState(OrderState state);
    List<Order> findAllByClientId(UUID id);
    void deleteById(UUID id);

    @Query("SELECT o FROM Order o JOIN o.steps s WHERE s.date = (SELECT MAX(s2.date) FROM OrderStep s2 WHERE s2.order = o) AND s.employeeId = :employeeId")
    List<Order> findAllOrdersWithEmployeeId(@Param("employeeId") UUID employeeId);

    @Query("SELECT o FROM Order o JOIN o.steps s WHERE s.date = (SELECT MAX(s2.date) FROM OrderStep s2 WHERE s2.order = o) AND s.employeeId = :employeeId AND s.date >= :date")
    List<Order> findEmployeeOrdersAfterDate(@Param("employeeId") UUID employeeId, @Param("date") Date date);

    @Query("SELECT o FROM Order o JOIN o.steps s WHERE s.date = (SELECT MAX(s2.date) FROM OrderStep s2 WHERE s2.order = o) AND s.employeeId = :employeeId AND s.date >= :startDate AND s.date <= :endDate")
    List<Order> findEmployeeOrdersInDateRange(@Param("employeeId") UUID employeeId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}

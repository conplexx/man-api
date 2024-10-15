package web2.man.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import web2.man.enums.OrderState;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

//solicitação
@Entity
@Data
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;
    @Column(nullable = false)
    private UUID clientId;

    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private UUID equipmentCategoryId;
    @Column(nullable = false, length = 30)
    private String equipmentDescription;
    @Column(nullable = false, length = 30)
    private String failureDescription;
    @Column(nullable = false)
    private OrderState state;
    @ElementCollection(targetClass = OrderStep.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "orderStep", joinColumns = @JoinColumn(name = "clientOrderId"))
    @Column(name = "orderStep")
    private List<OrderStep> steps;

}

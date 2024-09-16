package web2.man.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import web2.man.enums.ClientOrderState;

import java.util.Date;
import java.util.List;
import java.util.UUID;

//solicitação
@Entity
@Data
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private Date date;
    @Column(nullable = false, length = 30)
    private String equipmentDescription;
    @Column(nullable = false)
    private ClientOrderState state;

    @ElementCollection(targetClass = OrderStep.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "orderStep", joinColumns = @JoinColumn(name = "clientOrderId"))
    @Column(name = "orderStep")
    private List<OrderStep> steps;

}

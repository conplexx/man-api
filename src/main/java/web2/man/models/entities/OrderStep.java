package web2.man.models.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import web2.man.enums.OrderState;
import web2.man.models.embeddables.Budget;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "orderStep")
public class OrderStep implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;
    @Column(nullable = false)
    private OrderState state;
    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private UUID employeeId;

    @Column
    private UUID maintananceId;
    @Embedded
    private Budget budget;
    @Column(length = 30)
    private String clientRejectedReason; //caso seja rejeicao
}

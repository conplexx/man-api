package web2.man.models.entities;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import web2.man.enums.ClientOrderState;
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
    @Column(nullable = false)
    private UUID orderId;
    @Column(nullable = false)
    private ClientOrderState state;
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

package web2.man.models;


import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import web2.man.enums.ClientOrderState;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "order_step")
public class OrderStep {
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

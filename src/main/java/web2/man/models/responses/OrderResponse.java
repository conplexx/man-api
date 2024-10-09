package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web2.man.models.entities.Order;
import web2.man.models.entities.OrderStep;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {
    Order order;
    List<OrderStep> orderSteps;
}

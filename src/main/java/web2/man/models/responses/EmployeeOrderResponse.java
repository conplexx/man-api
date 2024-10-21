package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web2.man.enums.OrderState;
import web2.man.models.entities.EquipmentCategory;
import web2.man.models.entities.OrderStep;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOrderResponse {
    UUID id;
    ClientResponse client;
    Date date;
    EquipmentCategory equipmentCategory;
    String equipmentDescription;
    String failureDescription;
    OrderState state;
    List<OrderStep> steps;
}

package web2.man.dtos;

import jakarta.persistence.Column;
import lombok.Data;
import web2.man.models.EquipmentCategory;

@Data
public class ClientOrderDto {
    @Column(nullable = false, length = 30)
    private String equipmentDescription;
    @Column(nullable = false)
    private EquipmentCategory equipmentCategory;
    @Column(nullable = false, length = 30)
    private String failureDescription;
}

package web2.man.dtos;

import jakarta.persistence.Column;
import lombok.Data;

import java.util.UUID;

@Data
public class ClientOrderDto {
    @Column(nullable = false, length = 30)
    private String equipmentDescription;
    @Column(nullable = false)
    private UUID equipmentCategoryId;
    @Column(nullable = false, length = 30)
    private String failureDescription;
}

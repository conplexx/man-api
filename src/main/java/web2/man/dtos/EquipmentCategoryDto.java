package web2.man.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EquipmentCategoryDto {
    @NotBlank
    String name;
}

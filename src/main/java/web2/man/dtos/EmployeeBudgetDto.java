package web2.man.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeBudgetDto {
    @NotNull
    private double value;
    @NotNull
    private UUID employeeId;
    private String description;
}

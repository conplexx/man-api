package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web2.man.enums.UserRole;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
    String cpf;
    String name;
    String email;
    Date birthday;
}

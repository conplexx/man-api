package web2.man.models.responses;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web2.man.enums.UserRole;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    String cpf;
    String name;
    String email;
    String phone;
    UserRole role;
    AddressResponse address;
}

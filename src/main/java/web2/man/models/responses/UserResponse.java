package web2.man.models.responses;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import web2.man.enums.UserRole;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponse {
    final String cpf;
    final String name;
    final String email;
    final String phone;
    final UserRole role;
    final AddressResponse address;
}

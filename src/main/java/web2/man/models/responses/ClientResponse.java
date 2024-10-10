package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import web2.man.enums.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse {
    String cpf;
    String name;
    String email;
    String phone;
    AddressResponse address;
}

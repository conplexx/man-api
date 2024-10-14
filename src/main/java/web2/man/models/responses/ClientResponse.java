package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import web2.man.enums.UserRole;
import web2.man.models.entities.Address;
import web2.man.models.entities.Client;

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

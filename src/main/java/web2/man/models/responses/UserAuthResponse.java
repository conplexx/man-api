package web2.man.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import web2.man.enums.UserRole;
import web2.man.models.entities.Employee;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAuthResponse {
    TokenResponse accessToken;
    UserRole userRole;
    ClientResponse client;
    Employee employee;

    public UserAuthResponse(TokenResponse accessToken, ClientResponse client) {
        this.accessToken = accessToken;
        this.userRole = UserRole.CLIENT;
        this.client = client;
    }

    public UserAuthResponse(TokenResponse accessToken, Employee employee) {
        this.accessToken = accessToken;
        this.userRole = UserRole.EMPLOYEE;
        this.employee = employee;
    }
}

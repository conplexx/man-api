package web2.man.models.responses;

import lombok.Data;
import web2.man.enums.UserRole;
import web2.man.models.entities.Employee;

@Data
public class UserAuthResponse {
    TokenResponse accessToken;
    UserRole role;
    ClientResponse client;
    Employee employee;

    public UserAuthResponse(TokenResponse accessToken, ClientResponse client) {
        this.accessToken = accessToken;
        this.role = UserRole.CLIENT;
        this.client = client;
    }

    public UserAuthResponse(TokenResponse accessToken, Employee employee) {
        this.accessToken = accessToken;
        this.role = UserRole.EMPLOYEE;
        this.employee = employee;
    }
}

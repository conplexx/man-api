package web2.man.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import web2.man.enums.UserRole;

import java.util.UUID;

@Entity
@Data
@Table(name = "client")
public class Client extends User {
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private UUID addressId;
    @Column(nullable = false, length = 4) //4 numeros
    private String password;

    public Client() {
        this.setRole(UserRole.CLIENT);
    }
}

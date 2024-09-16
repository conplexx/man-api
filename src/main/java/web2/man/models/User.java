package web2.man.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import web2.man.enums.UserRole;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name = "user")
public class User implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, length = 11)
    private String cpf;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(length = 4) //4 numeros
    private String password;
    @Column(nullable = false)
    private UserRole role;
    @Column(nullable = false)
    private UUID addressId;
}

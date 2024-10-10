package web2.man.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "employee")
public class Employee extends User {
    @Column(nullable = false)
    private Date birthday;
    @Column(nullable = false)
    private String password;
}

package web2.man.models;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Data
@Embeddable
public class Budget {
    @Column(nullable = false)
    private double value;
    @Column(length = 30)
    private String description;
}

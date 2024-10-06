package web2.man.models.embeddables;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Embeddable
public class Budget {
    @Column(nullable = false)
    private double value;
    @Column(length = 30)
    private String description;
}

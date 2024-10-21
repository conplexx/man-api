package web2.man.models.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@Table(name = "revenue")
public class Revenue {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private Date date;
    @Column(nullable = false)
    private UUID userId;
    @Column(nullable = false)
    private UUID employeeId;
    @Column(nullable = false)
    private double value;
    @ManyToOne
    @JoinColumn(name = "equipmentCategoryId", referencedColumnName = "id", nullable = false)
    private EquipmentCategory equipmentCategory;
}

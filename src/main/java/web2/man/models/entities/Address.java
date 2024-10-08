package web2.man.models.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Data
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "uuid")
    private UUID id;

    @NotBlank
    private String zipCode;
    @NotBlank
    private String state;
    @NotBlank
    private String city;
    @NotBlank
    private String neighborhood;
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    private String complement;
}

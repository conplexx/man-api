package web2.man.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddressDto {
    @NotBlank
    private String zipCode;
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String complement;
    @NotBlank
    private String neighborhood;
    @NotBlank
    private String city;
    @NotBlank
    @Length(min = 2, max = 2)
    private String state;
}

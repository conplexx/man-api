package web2.man.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressDto {
    @NotBlank
    private String zipCode;
    @NotBlank
    private String street;
    @NotBlank
    private String number;
    @NotBlank
    private String neighborhood;
    @NotBlank
    private String city;
    @NotBlank
    private String state;
    @NotBlank
    private String country;
}

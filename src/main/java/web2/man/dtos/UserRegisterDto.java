package web2.man.dtos;

import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterDto {
    @NotBlank
    @Size(max = 11) //cpf pelado
    private String cpf;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotNull
    @Embedded
    private AddressDto address;
    @NotBlank
    private String phone;
}
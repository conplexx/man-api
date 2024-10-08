package web2.man.models.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponse {
    final String zipCode;
    final String state;
    final String city;
    final String neighborhood;
    final String street;
    final String number;
}

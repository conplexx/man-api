package web2.man.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRefreshTokenDto {
    @NotBlank
    private String authToken;
    @NotBlank
    private String refreshToken;
}
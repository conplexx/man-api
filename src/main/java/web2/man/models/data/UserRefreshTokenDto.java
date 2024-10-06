package web2.man.models.data;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRefreshTokenDto {
    @NotBlank
    private String authToken;
    @NotBlank
    private String refreshToken;
}
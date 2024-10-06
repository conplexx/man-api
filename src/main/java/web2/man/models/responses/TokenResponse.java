package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TokenResponse {
    final String authToken;
    final Date authExpiration;
    final String refreshToken;
    final Date refreshExpiration;
}

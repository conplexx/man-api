package web2.man.models.data;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class TokenExpiration {
    String token;
    Date expiration;
}

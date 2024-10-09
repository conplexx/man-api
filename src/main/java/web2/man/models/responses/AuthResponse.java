package web2.man.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import web2.man.models.entities.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    TokenResponse accessToken;
    UserResponse user;
}

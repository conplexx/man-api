package web2.man.models.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import web2.man.models.entities.User;

@Data
@RequiredArgsConstructor
public class AuthResponse {
    final TokenResponse accessToken;
    final UserResponse user;
}

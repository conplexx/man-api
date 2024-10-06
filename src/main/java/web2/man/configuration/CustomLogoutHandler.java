package web2.man.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import web2.man.models.entities.User;
import web2.man.services.AuthTokenService;
import web2.man.services.RefreshTokenService;
import web2.man.services.UserService;
import web2.man.util.JwtTokenUtil;

import java.util.Optional;
import java.util.UUID;

public class CustomLogoutHandler implements LogoutHandler {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    UserService userService;
    @Autowired
    AuthTokenService authTokenService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws AuthenticationException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        final String token = authHeader.substring(7);
        final UUID userId = UUID.fromString(jwtTokenUtil.extractSubject(token));

        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            boolean isTokenValid = false;
            Optional<User> userRequest = this.userService.findById(userId);
            if(userRequest.isPresent()){
                var userToken = authTokenService.findByToken(token);
                if(userToken.isPresent()) {
                    isTokenValid = jwtTokenUtil.isTokenValid(userToken.get().getToken(), userId);
                }
                if(isTokenValid) {
                    authTokenService.deleteByUserId(userId);
                    refreshTokenService.deleteByUserId(userId);
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpStatus.OK.value());
                    return;
                }
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}

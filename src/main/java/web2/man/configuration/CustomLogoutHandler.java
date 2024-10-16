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
import org.springframework.web.bind.annotation.CrossOrigin;
import web2.man.enums.UserRole;
import web2.man.services.AuthTokenService;
import web2.man.services.ClientService;
import web2.man.services.EmployeeService;
import web2.man.services.RefreshTokenService;
import web2.man.util.JwtTokenUtil;

import java.util.UUID;

public class CustomLogoutHandler implements LogoutHandler {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Autowired
    ClientService clientService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    AuthTokenService authTokenService;
    @Autowired
    RefreshTokenService refreshTokenService;
    @CrossOrigin(origins = "http://localhost:4200")
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws AuthenticationException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return;
        }
        final String token = authHeader.substring(7);
        final UUID userId = UUID.fromString(jwtTokenUtil.extractSubject(token));
        final UserRole userRole = (UserRole) jwtTokenUtil.extractClaim(token, "role");

        try{
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                boolean isTokenValid = authTokenService.existsByToken(token);
                boolean hasUser = clientService.existsById(userId) || employeeService.existsById(userId);
                if(hasUser && isTokenValid){
                    authTokenService.deleteByUserIdAndUserRole(userId, userRole);
                    refreshTokenService.deleteByUserIdAndUserRole(userId, userRole);
                    SecurityContextHolder.clearContext();
                    response.setStatus(HttpStatus.OK.value());
                    return;
                }
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}

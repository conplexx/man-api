package web2.man.configuration;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import web2.man.models.entities.User;
import web2.man.services.AuthTokenService;
import web2.man.services.UserService;
import web2.man.util.JwtTokenUtil;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    final AuthTokenService tokenService;
    @Autowired
    final UserService userService;
    @Autowired
    final JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null|| !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String token = authHeader.substring(7);
        final UUID userId = UUID.fromString(jwtTokenUtil.extractSubject(token));
        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            boolean isTokenValid = false;
            User user;
            Optional<User> userRequest = this.userService.findById(userId);
            if(userRequest.isPresent()){
                user = userRequest.get();
                var userToken = tokenService.findByToken(token);
                if(userToken.isPresent()) {
                    isTokenValid = jwtTokenUtil.isTokenValid(userToken.get().getToken(), userId);
                }
                if(isTokenValid) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userId, null, user.getAuthorities()
                    );
                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}

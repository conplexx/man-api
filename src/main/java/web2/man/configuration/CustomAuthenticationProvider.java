package web2.man.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import web2.man.models.entities.User;
import web2.man.services.UserService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    final UserService userService;
    @Autowired
    final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws UsernameNotFoundException, BadCredentialsException {
        String requestLogin = authentication.getName();
        String requestPassword = authentication.getCredentials().toString();
        var optionalUser = userService.findByEmail(requestLogin);
        if(optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("Usuário não encontrado.");
        }
        User user = optionalUser.get();
        String userPassword = user.getPassword();
        boolean isAuthenticated = passwordEncoder.matches(requestPassword, userPassword);

        if (!isAuthenticated) {
            throw new BadCredentialsException("Credenciais inválidas.");
        }

        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
package web2.man.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import web2.man.models.data.UserDescription;
import web2.man.models.entities.Client;
import web2.man.models.entities.Employee;
import web2.man.services.ClientService;
import web2.man.services.EmployeeService;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    final ClientService clientService;
    @Autowired
    final EmployeeService employeeService;
    @Autowired
    final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws UsernameNotFoundException, BadCredentialsException {
        String loginEmail = authentication.getPrincipal().toString();
        String loginPassword = authentication.getCredentials().toString();

        var optionalClient = clientService.findByEmail(loginEmail);
        var optionalEmployee = employeeService.findByEmail(loginEmail);
        var user = optionalClient.isPresent() ? optionalClient.get() : optionalEmployee.get();


        String userPassword = optionalClient
                .map(Client::getPassword)
                .orElseGet(() -> optionalEmployee
                        .map(Employee::getPassword)
                        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."))
                );

//        boolean isAuthenticated = passwordEncoder.matches(loginPassword, userPassword);
        boolean isAuthenticated = loginPassword.equals(userPassword);

        if (!isAuthenticated) {
            throw new BadCredentialsException("Credenciais inválidas.");
        }
        var userDescription = new UserDescription(user.getId(), user.getRole());

        return new UsernamePasswordAuthenticationToken(userDescription, null, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
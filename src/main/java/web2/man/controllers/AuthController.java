package web2.man.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web2.man.configuration.CustomAuthenticationProvider;
import web2.man.dtos.UserLoginDto;
import web2.man.dtos.ClientRegisterDto;
import web2.man.dtos.UserRefreshTokenDto;
import web2.man.enums.UserRole;
import web2.man.models.data.TokenExpiration;
import web2.man.models.entities.*;
import web2.man.models.responses.*;
import web2.man.services.*;
import web2.man.util.JwtTokenUtil;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth", consumes = "application/json", produces = "application/json")
public class AuthController {
    @Autowired
    final ClientService clientService;
    @Autowired
    final EmployeeService employeeService;
    @Autowired
    final AddressService addressService;
    @Autowired
    final AuthTokenService authTokenService;
    @Autowired
    final RefreshTokenService refreshTokenService;
    @Autowired
    final JwtTokenUtil jwtTokenUtil;
    @Autowired
    final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    final CustomAuthenticationProvider authenticationProvider;

    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler({ Exception.class })
        public ResponseEntity<BaseResponse> handleGenericException(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(ex.getMessage()));
        }
        @ExceptionHandler({ BadCredentialsException.class })
        public ResponseEntity<BaseResponse> handleBadCredentialsExceptionException(BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(ex.getMessage()));
        }
        @ExceptionHandler({ UsernameNotFoundException.class })
        public ResponseEntity<BaseResponse> handleUsernameNotFoundException(UsernameNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse(ex.getMessage()));
        }
    }
    @PostMapping("/client-register")
    public ResponseEntity<BaseResponse> register(@RequestBody @Valid ClientRegisterDto registerDto) throws RuntimeException {
        if (clientService.existsByCpf(registerDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse("Usuário com esse CPF já existe."));
        }
        if(clientService.existsByEmail(registerDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse("Usuário com esse email já existe."));
        }
        try {
            Client client = storeNewClient(registerDto);
            ClientResponse clientResponse = generateResponseForClient(client);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(clientResponse));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao registrar usuário.");
        }
    }
    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@RequestBody @Valid UserLoginDto userLoginDto) throws BadCredentialsException {
        try {
            var credentials = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(),
                    userLoginDto.getPassword());
            Authentication auth = authenticationProvider.authenticate(credentials);

            TokenResponse tokenResponse;
            if(auth.getAuthorities().contains(UserRole.CLIENT)) {
                Client client = (Client) auth.getPrincipal();
                authTokenService.deleteByUserIdAndUserRole(client.getId(), UserRole.CLIENT);
                refreshTokenService.deleteByUserIdAndUserRole(client.getId(), UserRole.CLIENT);
                tokenResponse = generateNewAccessTokenForUser(client.getId(), UserRole.CLIENT);
                var clientResponse = generateResponseForClient(client);
                var authResponse = new UserAuthResponse(tokenResponse, clientResponse);
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(authResponse));
            }
            else {
                Employee employee = (Employee) auth.getPrincipal();
                authTokenService.deleteByUserIdAndUserRole(employee.getId(), UserRole.EMPLOYEE);
                refreshTokenService.deleteByUserIdAndUserRole(employee.getId(), UserRole.EMPLOYEE);
                tokenResponse = generateNewAccessTokenForUser(employee.getId(), UserRole.EMPLOYEE);
                var authResponse = new UserAuthResponse(tokenResponse, employee);
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(authResponse));
            }
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Credenciais inválidas.");
        }
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                          @RequestBody @Valid UserRefreshTokenDto userRefreshTokenDto) {
        var authToken = userRefreshTokenDto.getAuthToken();
        final UUID userId = UUID.fromString(jwtTokenUtil.extractSubject(authToken));
        UserRole role = jwtTokenUtil.extractClaim(authToken, "role");
        if (!jwtTokenUtil.isTokenValid(authToken, userId)) {
            authTokenService.deleteByUserIdAndUserRole(userId, role);
            refreshTokenService.deleteByUserIdAndUserRole(userId, role);
        }
        var accessToken = generateNewAccessTokenForUser(userId, role);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(accessToken));
    }

    private Client storeNewClient(ClientRegisterDto registerDto) {
        Client client = new Client();
        Address address = new Address();
        BeanUtils.copyProperties(registerDto, client);
        BeanUtils.copyProperties(registerDto.getAddress(), address);
        Address createdAddress = addressService.save(address);
        client.setAddressId(createdAddress.getId());
        client.setPassword(generateNewUserPassword()); //TODO ENCODAR SENHA
        Client createdClient = clientService.save(client);
        return createdClient;
    }

    private TokenResponse generateNewAccessTokenForUser(UUID userId, UserRole role) {
        var auth = new AuthToken();
        Map<String, Object> claims = Map.of("role", role);
        TokenExpiration generatedAuth = jwtTokenUtil.generateToken(claims, userId);
        auth.setToken(generatedAuth.getToken());
        auth.setExpirationDate(generatedAuth.getExpiration());
        auth.setUserId(userId);
        auth.setUserRole(role);
        String newAuth = authTokenService.save(auth).getToken();
        var refresh = new RefreshToken();
        TokenExpiration generatedRefresh = jwtTokenUtil.generateRefreshToken();
        refresh.setToken(generatedRefresh.getToken());
        refresh.setExpirationDate(generatedRefresh.getExpiration());
        refresh.setUserId(userId);
        refresh.setUserRole(role);
        var newRefresh = refreshTokenService.save(refresh).getToken();
        return new TokenResponse(newAuth, newRefresh);
    }

    private ClientResponse generateResponseForClient(Client client) {
        var clientResponse = new ClientResponse();
        BeanUtils.copyProperties(client, clientResponse);
        Address address = addressService.findById(client.getAddressId()).get();
        var addressResponse = new AddressResponse();
        BeanUtils.copyProperties(address, addressResponse);
        clientResponse.setAddress(addressResponse);
        return clientResponse;
    }

    private String generateNewUserPassword() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }
        return sb.toString();
    }

    private void sendUserPasswordEmail(String email, String password) {
        //TODO MANDAR EMAIL COM SENHA GERADA
    }

}

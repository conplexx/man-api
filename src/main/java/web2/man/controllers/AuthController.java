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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import web2.man.configuration.CustomAuthenticationProvider;
import web2.man.dtos.UserLoginDto;
import web2.man.dtos.UserRegisterDto;
import web2.man.models.data.UserRefreshTokenDto;
import web2.man.models.entities.Address;
import web2.man.models.entities.AuthToken;
import web2.man.models.entities.RefreshToken;
import web2.man.models.entities.User;
import web2.man.models.responses.AuthResponse;
import web2.man.models.responses.TokenResponse;
import web2.man.services.AddressService;
import web2.man.services.AuthTokenService;
import web2.man.services.RefreshTokenService;
import web2.man.services.UserService;
import web2.man.util.JwtTokenUtil;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    final UserService userService;
    @Autowired
    final AddressService addressService;
    @Autowired
    final RefreshTokenService refreshTokenService;
    @Autowired
    final AuthTokenService authTokenService;
    @Autowired
    final JwtTokenUtil jwtTokenUtil;
    @Autowired
    final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    final CustomAuthenticationProvider authenticationProvider;

    @PostMapping("/autocadastro")
    public ResponseEntity<Object> register(@RequestBody @Valid UserRegisterDto userRegisterDto) throws InterruptedException, ExecutionException {
        if (userService.existsByCpf(userRegisterDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário com esse CPF já existe.");
        }
        if(userService.existsByEmail(userRegisterDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário com esse email já existe.");
        }
        try {
            User user = storeNewUser(userRegisterDto);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) throws InterruptedException, ExecutionException {
        try {
            var credentials = new UsernamePasswordAuthenticationToken(userLoginDto.getEmail(),
                    userLoginDto.getPassword());
            Authentication auth = authenticationProvider.authenticate(credentials);
            User user = (User) auth.getPrincipal();
            authTokenService.deleteByUserId(user.getId());
            refreshTokenService.deleteByUserId(user.getId());
            var authResponse = new AuthResponse(generateNewAccessTokenForUser(user.getId()), user);
            return ResponseEntity.ok().body(authResponse);
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login ou senha incorretos");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Object> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                          @RequestBody @Valid UserRefreshTokenDto userRefreshTokenDto) {
        var authToken = userRefreshTokenDto.getAuthToken();
        var refreshToken = userRefreshTokenDto.getRefreshToken();
        final UUID userId = UUID.fromString(jwtTokenUtil.extractSubject(authToken));
        if (!jwtTokenUtil.isTokenValid(authToken, userId)) {
            authTokenService.deleteByUserId(userId);
            refreshTokenService.deleteByUserId(userId);
        }
        var body = generateNewAccessTokenForUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }

    private User storeNewUser(UserRegisterDto userRegisterDto) {
        User user = new User();
        Address address = new Address();
        BeanUtils.copyProperties(userRegisterDto, user);
        BeanUtils.copyProperties(userRegisterDto.getAddress(), address);
        Address createdAddress = addressService.save(address);
        user.setAddressId(createdAddress.getId());
        User createdUser = userService.save(user);
        return createdUser;
    }

    private TokenResponse generateNewAccessTokenForUser(UUID userId) {
        var auth = new AuthToken();
        var generatedAuth = jwtTokenUtil.generateToken(userId);
        auth.setToken(generatedAuth.getToken());
        auth.setExpirationDate(generatedAuth.getExpiration());
        auth.setUserId(userId);
        authTokenService.save(auth).getToken();
        var refresh = new RefreshToken();
        var generatedRefresh = jwtTokenUtil.generateRefreshToken();
        refresh.setToken(generatedRefresh.getToken());
        refresh.setExpirationDate(generatedRefresh.getExpiration());
        refresh.setUserId(userId);
        refreshTokenService.save(refresh).getToken();
        return new TokenResponse(auth.getToken(), auth.getExpirationDate(), refresh.getToken(),
                refresh.getExpirationDate());
    }

    private void sendEmail() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        String password = sb.toString();
        //TODO SEND EMAIL
    }
}

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
import web2.man.dtos.UserRegisterDto;
import web2.man.enums.UserRole;
import web2.man.models.data.UserRefreshTokenDto;
import web2.man.models.entities.Address;
import web2.man.models.entities.AuthToken;
import web2.man.models.entities.RefreshToken;
import web2.man.models.entities.User;
import web2.man.models.responses.*;
import web2.man.services.AddressService;
import web2.man.services.AuthTokenService;
import web2.man.services.RefreshTokenService;
import web2.man.services.UserService;
import web2.man.util.JwtTokenUtil;

import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "auth", consumes = "application/json", produces = "application/json")
public class AuthController {
    @Autowired
    final UserService userService;
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
        public ResponseEntity<BaseResponse> handleUsernameNotFoundException(Exception ex) {
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
    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@RequestBody @Valid UserRegisterDto userRegisterDto) throws RuntimeException {
        if (userService.existsByCpf(userRegisterDto.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse("Usuário com esse CPF já existe."));
        }
        if(userService.existsByEmail(userRegisterDto.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new BaseResponse("Usuário com esse email já existe."));
        }
        try {
            User user = storeNewUser(userRegisterDto);
            UserResponse userResponse = generateResponseForUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(userResponse));
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
            User user = (User) auth.getPrincipal();
            authTokenService.deleteByUserId(user.getId());
            refreshTokenService.deleteByUserId(user.getId());
            var tokenResponse = generateNewAccessTokenForUser(user.getId());
            var userResponse = generateResponseForUser(user);
            var authResponse = new AuthResponse(tokenResponse, userResponse);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(authResponse));
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Credenciais inválidas.");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<BaseResponse> refresh(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
                                          @RequestBody @Valid UserRefreshTokenDto userRefreshTokenDto) {
        var authToken = userRefreshTokenDto.getAuthToken();
        var refreshToken = userRefreshTokenDto.getRefreshToken();
        final UUID userId = UUID.fromString(jwtTokenUtil.extractSubject(authToken));
        if (!jwtTokenUtil.isTokenValid(authToken, userId)) {
            authTokenService.deleteByUserId(userId);
            refreshTokenService.deleteByUserId(userId);
        }
        var accessToken = generateNewAccessTokenForUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(accessToken));
    }

    private User storeNewUser(UserRegisterDto userRegisterDto) {
        User user = new User();
        Address address = new Address();
        BeanUtils.copyProperties(userRegisterDto, user);
        BeanUtils.copyProperties(userRegisterDto.getAddress(), address);
        Address createdAddress = addressService.save(address);
        user.setAddressId(createdAddress.getId());
        user.setPassword(generateNewUserPassword()); //TODO ENCODAR SENHA
        user.setRole(UserRole.CLIENT);
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
        return new TokenResponse(auth.getToken(), refresh.getToken());
    }

    private UserResponse generateResponseForUser(User user) {
        var userResponse = new UserResponse();
        BeanUtils.copyProperties(user, userResponse);
        Address address = addressService.findById(user.getAddressId()).get();
        var addressResponse = new AddressResponse();
        BeanUtils.copyProperties(address, addressResponse);
        userResponse.setAddress(addressResponse);
        return userResponse;
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

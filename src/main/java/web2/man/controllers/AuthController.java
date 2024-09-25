package web2.man.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web2.man.dtos.UserLoginDto;
import web2.man.dtos.UserRegisterDto;
import web2.man.models.Address;
import web2.man.models.User;
import web2.man.services.AddressService;
import web2.man.services.UserService;

import java.util.Random;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    final UserService userService;
    @Autowired
    final AddressService addressService;

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
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar usuário.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto) throws InterruptedException, ExecutionException {
        try {
            User user = userService.findByEmail(userLoginDto.getEmail()).get();
            //TODO SALT/HASH
            if(user.getPassword() == userLoginDto.getPassword()){
                //TODO JWT
                return ResponseEntity.status(HttpStatus.OK).body("logado");
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("senha incorreta");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não existe");
        }
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

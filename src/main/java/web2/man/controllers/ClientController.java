package web2.man.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/client")
public class ClientController {
    @Autowired
    final UserService userService;
    @Autowired
    final AddressService addressService;

    @GetMapping("/home")
    public ResponseEntity<Object> getHome() throws InterruptedException, ExecutionException {
        
    }
}

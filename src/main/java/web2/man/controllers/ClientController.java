package web2.man.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web2.man.dtos.ClientOrderDto;
import web2.man.models.entities.Order;
import web2.man.services.AddressService;
import web2.man.services.OrderService;
import web2.man.services.UserService;
import web2.man.util.HeaderUtil;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    @Autowired
    final UserService userService;
    @Autowired
    final AddressService addressService;
    @Autowired
    final OrderService orderService;
    @Autowired
    final HeaderUtil headerUtil;

    @GetMapping("/home")
    public ResponseEntity<Object> getHome(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader) throws InterruptedException, ExecutionException {
        try {
            UUID clientId = headerUtil.getUserIdFromAuthHeader(authHeader).get();
            List<Order> orders = orderService.findAllByClientId(clientId);
            return ResponseEntity.status(HttpStatus.OK).body(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar pedidos.");
        }
    }

    @PostMapping("/order")
    public ResponseEntity<Object> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody @Valid ClientOrderDto clientOrderDto) throws InterruptedException, ExecutionException {
        try {
            Order order = new Order();
            BeanUtils.copyProperties(clientOrderDto, order);
            UUID userId = headerUtil.getUserIdFromAuthHeader(authHeader).get();
            order.setClientId(userId);
            Order newOrder = orderService.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(newOrder);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar pedido.");
        }
    }
}

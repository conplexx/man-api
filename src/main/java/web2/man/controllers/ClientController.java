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
import web2.man.dtos.ClientRegisterDto;
import web2.man.enums.OrderState;
import web2.man.models.entities.Address;
import web2.man.models.entities.Client;
import web2.man.models.entities.Order;
import web2.man.models.entities.OrderStep;
import web2.man.models.responses.BaseResponse;
import web2.man.models.responses.ClientResponse;
import web2.man.models.responses.OrderResponse;
import web2.man.services.*;
import web2.man.util.HeaderUtil;
import web2.man.util.ResponseUtil;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "cliente", consumes = "application/json", produces = "application/json")
public class ClientController {
    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler({ Exception.class })
        public ResponseEntity<BaseResponse> handleUsernameNotFoundException(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(ex.getMessage()));
        }
    }
    @Autowired
    final ClientService clientService;
    @Autowired
    final AddressService addressService;
    @Autowired
    final OrderService orderService;
    @Autowired
    final OrderStepService orderStepService;
    @Autowired
    final EquipmentCategoryService equipmentCategoryService;
    @Autowired
    final HeaderUtil headerUtil;
    @Autowired
    final ResponseUtil responseUtil;

    @PostMapping
    public ResponseEntity<BaseResponse> postUser(@RequestBody @Valid ClientRegisterDto clientDto) { //TODO tirar
        try {
            Client client = new Client();
            Address address = new Address();
            BeanUtils.copyProperties(clientDto, client);
            BeanUtils.copyProperties(clientDto.getAddress(), address);
            Address createdAddress = addressService.save(address);
            client.setAddressId(createdAddress.getId());
            client.setPassword("1234");
            Client createdClient = clientService.save(client);
            ClientResponse clientResponse = responseUtil.generateClientResponse(createdClient);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(clientResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("mas se é burro hein fi"));
        }
    }

    @GetMapping("/home")
    public ResponseEntity<BaseResponse> getHome(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader)
            throws InterruptedException, ExecutionException {
        try {
            UUID clientId = headerUtil.getUserIdFromAuthHeader(authHeader).get();
            List<Order> orders = orderService.findAllByClientId(clientId);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(orders));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("Erro ao buscar pedidos."));
        }
    }

    @GetMapping("/categorias-de-equipamento")
    public ResponseEntity<BaseResponse> getEquipmentCategories() {
        try {
            List categories = equipmentCategoryService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("Erro ao buscar categorias de equipamento."));
        }
    }

    @PostMapping("/manutencao")
    public ResponseEntity<BaseResponse> createOrder(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody @Valid ClientOrderDto clientOrderDto
    ) throws InterruptedException, ExecutionException {
        try {
            Order order = new Order();
            BeanUtils.copyProperties(clientOrderDto, order);
            UUID userId = headerUtil.getUserIdFromAuthHeader(authHeader).get();
            order.setClientId(userId);
            order.setState(OrderState.OPEN);
            order.setDate(new Date());
            Order newOrder = orderService.save(order);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(newOrder));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("Erro ao criar pedido."));
        }
    }

    @PostMapping("/pedido")
    public ResponseEntity<BaseResponse> getBudget(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestBody @Valid UUID orderId
    ) throws InterruptedException, ExecutionException {
        try {
            Order order = orderService.findById(orderId).get();
            UUID userId = headerUtil.getUserIdFromAuthHeader(authHeader).get();
            if(!order.getClientId().equals(userId)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new BaseResponse("Pedido não pertence ao usuário."));
            }
            List<OrderStep> orderSteps = orderStepService.findAllByOrderId(orderId);
            var orderResponse = new OrderResponse(order, orderSteps);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(orderResponse));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse("Erro ao buscar pedido."));
        }
    }
}

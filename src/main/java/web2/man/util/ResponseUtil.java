package web2.man.util;

import jakarta.persistence.Column;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import web2.man.models.entities.Address;
import web2.man.models.entities.Client;
import web2.man.models.entities.EquipmentCategory;
import web2.man.models.entities.Order;
import web2.man.models.responses.AddressResponse;
import web2.man.models.responses.ClientResponse;
import web2.man.models.responses.EmployeeOrderResponse;
import web2.man.services.AddressService;
import web2.man.services.ClientService;
import web2.man.services.EquipmentCategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResponseUtil {
    @Autowired
    private AddressService addressService;
    @Autowired
    private EquipmentCategoryService equipmentCategoryService;
    @Autowired
    private ClientService clientService;

    public ClientResponse generateClientResponse(Client client) {
        var clientResponse = new ClientResponse();
        BeanUtils.copyProperties(client, clientResponse);
        Address address = addressService.findById(client.getAddressId()).get();
        var addressResponse = new AddressResponse();
        BeanUtils.copyProperties(address, addressResponse);
        clientResponse.setAddress(addressResponse);
        return clientResponse;
    }

    public EmployeeOrderResponse generateEmployeeOrderResponse(Order order) {
        var orderResponse = new EmployeeOrderResponse();
        BeanUtils.copyProperties(order, orderResponse);
        EquipmentCategory equipmentCategory = order.getEquipmentCategory();
        orderResponse.setEquipmentCategory(equipmentCategory);
        Client client = clientService.findById(order.getClientId()).get();
        ClientResponse clientResponse = this.generateClientResponse(client);
        orderResponse.setClient(clientResponse);
        return orderResponse;
    }

    public List<EmployeeOrderResponse> generateEmployeeOrderResponseList(List<Order> orders) {
        return orders.stream().map(order -> this.generateEmployeeOrderResponse(order)).collect(Collectors.toList());
    }
}

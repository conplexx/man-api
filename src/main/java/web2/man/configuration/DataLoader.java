package web2.man.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web2.man.enums.OrderState;
import web2.man.enums.UserRole;
import web2.man.models.entities.*;
import web2.man.services.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired private EquipmentCategoryService equipmentCategoryService;
    @Autowired private AddressService addressService;
    @Autowired private EmployeeService employeeService;
    @Autowired private ClientService clientService;
    @Autowired private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {
        //5 categorias (Notebook, Desktop, Impressora, Mouse, Teclado)
        if(equipmentCategoryService.findAll().size() == 0) {
            EquipmentCategory ec1 = new EquipmentCategory();
            ec1.setName("Notebook");
            equipmentCategoryService.save(ec1);
            EquipmentCategory ec2 = new EquipmentCategory();
            ec2.setName("Desktop");
            equipmentCategoryService.save(ec2);
            EquipmentCategory ec3 = new EquipmentCategory();
            ec3.setName("Impressora");
            equipmentCategoryService.save(ec3);
            EquipmentCategory ec4 = new EquipmentCategory();
            ec4.setName("Mouse");
            equipmentCategoryService.save(ec4);
            EquipmentCategory ec5 = new EquipmentCategory();
            ec5.setName("Teclado");
            equipmentCategoryService.save(ec5);
        }

        if(addressService.findAll().size() == 0) {
            Address ad1 = new Address();
            ad1.setZipCode("80320050");
            ad1.setState("PR");
            ad1.setCity("Curitiba");
            ad1.setNeighborhood("Vila Izabel");
            ad1.setStreet("Rua do Caos");
            ad1.setNumber("24");
            addressService.save(ad1);
        }

        //2 funcionários (Maria e Mário)
        if(employeeService.findAll().size() == 0){
            Employee maria = new Employee();
            maria.setName("Maria");
            maria.setCpf("88684642090");
            maria.setEmail("maria@funcionario.com");
            maria.setPassword("1234");
            maria.setBirthday(new Date());
            employeeService.save(maria);

            Employee mario = new Employee();
            maria.setName("Mário");
            maria.setCpf("59201895097");
            maria.setEmail("mario@funcionario.com");
            maria.setPassword("1234");
            maria.setBirthday(new Date());
            employeeService.save(mario);
        }

        //4 clientes (João, Josué, Joana, Joaquina)
        if(clientService.findByEmail("josue@cliente.com").isEmpty()){
            var optAd = addressService.findAll().stream().findFirst();
            if(optAd.isPresent()) {
                var addressId = optAd.get().getId();
                var jozueh = new Client();
                jozueh.setPassword("1234");
                jozueh.setEmail("josue@cliente.com");
                jozueh.setName("Josué");
                jozueh.setRole(UserRole.CLIENT);
                jozueh.setCpf("00000000000");
                jozueh.setPhone("00000000000");
                jozueh.setAddressId(addressId);
                clientService.save(jozueh);
            }
        }

//        if(clientService.findByEmail("josue@cliente.com").isPresent()){
//            Client josu = clientService.findByEmail("josue@cliente.com").get();
//            var or = new Order();
//            or.setDate(new Date());
//            or.setSteps(new ArrayList<OrderStep>());
//            or.setClientId(josu.getId());
//            or.setEquipmentDescription("pc da xuxa");
//            or.setFailureDescription("foi atropelado");
//            or.setState(OrderState.OPEN);
//            EquipmentCategory ec = equipmentCategoryService.findAll().stream().findFirst().get();
//            or.setEquipmentCategory(ec);
//            orderService.save(or);
//        }
    }
}
package web2.man.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web2.man.models.entities.Address;
import web2.man.models.entities.Employee;
import web2.man.models.entities.EquipmentCategory;
import web2.man.services.AddressService;
import web2.man.services.ClientService;
import web2.man.services.EmployeeService;
import web2.man.services.EquipmentCategoryService;

import java.util.Date;
import java.util.UUID;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired private EquipmentCategoryService equipmentCategoryService;
    @Autowired private AddressService addressService;
    @Autowired private EmployeeService employeeService;
    @Autowired private ClientService clientService;

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
            UUID addressId = addressService.save(ad1).getId();
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
        }

        //4 clientes (João, José, Joana, Joaquina)
        if(employeeService.findAll().size() == 0){

        }
    }
}
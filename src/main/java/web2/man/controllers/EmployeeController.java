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
import web2.man.dtos.NewEmployeeDto;
import web2.man.enums.UserRole;
import web2.man.models.entities.Employee;
import web2.man.models.responses.BaseResponse;
import web2.man.services.EmployeeService;
import web2.man.services.EquipmentCategoryService;
import web2.man.services.OrderService;
import web2.man.services.OrderStepService;
import web2.man.util.HeaderUtil;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "funcionario", consumes = "application/json", produces = "application/json")
public class EmployeeController {
    @ControllerAdvice
    public class GlobalExceptionHandler {
        @ExceptionHandler({ Exception.class })
        public ResponseEntity<BaseResponse> handleUsernameNotFoundException(Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse(ex.getMessage()));
        }
    }
    @Autowired
    final EmployeeService employeeService;
    @Autowired
    final OrderService orderService;
    @Autowired
    final OrderStepService orderStepService;
    @Autowired
    final EquipmentCategoryService equipmentCategoryService;
    @Autowired
    final HeaderUtil headerUtil;

    @PostMapping
//    public ResponseEntity<BaseResponse> createEmployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody @Valid NewEmployeeDto employeeDto) {
    public ResponseEntity<BaseResponse> createEmployee(@RequestBody @Valid NewEmployeeDto employeeDto) throws Exception {
        try {
            Employee employee = new Employee();
            BeanUtils.copyProperties(employeeDto, employee);
            employee.setRole(UserRole.EMPLOYEE);
            employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(employee));
        } catch (Exception e) {
            throw new Exception("Erro ao criar funcionário.");
        }
    }
}

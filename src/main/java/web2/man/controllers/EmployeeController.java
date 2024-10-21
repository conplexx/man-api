package web2.man.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web2.man.dtos.EmployeeBudgetDto;
import web2.man.dtos.EquipmentCategoryDto;
import web2.man.dtos.NewEmployeeDto;
import web2.man.enums.EmployeeOrderFilterType;
import web2.man.enums.FinancialReportType;
import web2.man.models.data.FinancialReportEquipmentCategoryInfo;
import web2.man.models.data.FinancialReportRevenueDay;
import web2.man.models.entities.Employee;
import web2.man.models.entities.EquipmentCategory;
import web2.man.models.entities.Order;
import web2.man.models.entities.Revenue;
import web2.man.models.responses.BaseResponse;
import web2.man.models.responses.EmployeeOrderResponse;
import web2.man.models.responses.FinancialReportEquipmentCategoryResponse;
import web2.man.models.responses.FinancialReportTimeResponse;
import web2.man.services.*;
import web2.man.util.HeaderUtil;
import web2.man.util.ResponseUtil;

import java.util.*;
import java.util.stream.Collectors;

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
    final ClientService clientService;
    @Autowired
    final OrderService orderService;
    @Autowired
    final OrderStepService orderStepService;
    @Autowired
    final EquipmentCategoryService equipmentCategoryService;
    @Autowired
    final RevenueService revenueService;
    @Autowired
    final HeaderUtil headerUtil;
    @Autowired
    final ResponseUtil responseUtil;

    @GetMapping
    public ResponseEntity<BaseResponse> getAllEmployees() throws Exception {
        try {
            List<Employee> employees = employeeService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(employees));
        } catch (Exception e) {
            throw new Exception("Erro ao buscar funcionários.");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getEmployee(@PathVariable UUID id) throws Exception {
        try{
            var optional = employeeService.findById(id);
            if(optional.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("Funcionário não encontrado."));
            }
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(optional.get()));
        } catch (Exception e) {
            throw new Exception("Erro ao buscar funcionário.");
        }
    }

    @PostMapping
    //TODO fazer direito insert de funcionario
    //public ResponseEntity<BaseResponse> createEmployee(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader, @RequestBody @Valid NewEmployeeDto employeeDto) {
    public ResponseEntity<BaseResponse> createEmployee(@RequestBody @Valid NewEmployeeDto employeeDto) throws Exception {
        try {
            var employee = new Employee();
            BeanUtils.copyProperties(employeeDto, employee);
            employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(new BaseResponse(employee));
        } catch (Exception e) {
            throw new Exception("Erro ao criar funcionário.");
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BaseResponse> editEmployee(@PathVariable UUID id, Employee employee) throws Exception {
        try {
            if(id != employee.getId()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("Dados de funcionário inválidos."));
            }
            Employee edited = employeeService.save(employee);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(edited));
        } catch (Exception e) {
            throw new Exception("Erro ao editar funcionário.");
        }
    }

    @GetMapping("/home")
    public ResponseEntity<BaseResponse> getHome() throws Exception {
        try {
            List<Order> orders = orderService.findAllWithOpenState();
            List<EmployeeOrderResponse> orderResponses = responseUtil.generateEmployeeOrderResponseList(orders);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(orderResponses));
        } catch (Exception e) {
            throw new Exception("Erro ao buscar pedidos.");
        }
    }

    @GetMapping("/categorias-de-equipamento")
    public ResponseEntity<BaseResponse> getAllEquipmentCategories() throws Exception {
        try {
            List<EquipmentCategory> equipmentCategories = equipmentCategoryService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(equipmentCategories));
        } catch (Exception e) {
            throw new Exception("Erro.");
        }
    }

    @PostMapping("/categorias-de-equipamento")
    public ResponseEntity<BaseResponse> createNewEquipmentCategory(@RequestBody @Valid EquipmentCategoryDto equipmentCategoryDto) throws Exception {
        try {
            var equipmentCategory = new EquipmentCategory();
            BeanUtils.copyProperties(equipmentCategoryDto, equipmentCategory);
            EquipmentCategory saved = equipmentCategoryService.save(equipmentCategory);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(saved));
        } catch (Exception e) {
            throw new Exception("Erro.");
        }
    }

    @PatchMapping("/categorias-de-equipamento/{id}")
    public ResponseEntity<BaseResponse> editEquipmentCategory(@PathVariable UUID id, EquipmentCategory equipmentCategory) throws Exception {
        try {
            if(id != equipmentCategory.getId()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("Dados de categoria de equipamento inválidos."));
            }
            EquipmentCategory edited = equipmentCategoryService.save(equipmentCategory);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(edited));
        } catch (Exception e) {
            throw new Exception("Erro.");
        }
    }

    @DeleteMapping("/categorias-de-equipamento/{id}")
    public ResponseEntity<BaseResponse> deleteEquipmentCategory(@PathVariable UUID id) throws Exception {
        try {
            equipmentCategoryService.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse());
        } catch (Exception e) {
            throw new Exception("Erro.");
        }
    }
    @GetMapping("/pedido")
    public ResponseEntity<BaseResponse> getOrders(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader,
            @RequestParam EmployeeOrderFilterType filterType,
            @RequestParam(required = false) Date startDate,
            @RequestParam(required = false) Date endDate)
        throws Exception {
        try {
            UUID employeeId = headerUtil.getUserIdFromAuthHeader(authHeader).get();
            if(filterType == EmployeeOrderFilterType.TODAY) {
                List<Order> orders = orderService.findEmployeeOrdersWithinLast24Hours(employeeId);
                var ordersResponse = responseUtil.generateEmployeeOrderResponseList(orders);
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(ordersResponse));
            }
            if(filterType == EmployeeOrderFilterType.DATE_PERIOD){
                if(startDate == null || endDate == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("faltaram as datas de inicio e fim"));
                }
                List<Order> orders = orderService.findEmployeeOrdersInDateRange(employeeId, startDate, endDate);
                var ordersResponse = responseUtil.generateEmployeeOrderResponseList(orders);
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(ordersResponse));
            }
            else{
                var orders = orderService.findAllOrdersWithEmployeeId(employeeId);
                var orderResponse = responseUtil.generateEmployeeOrderResponseList(orders);
                return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(orderResponse));
            }
        } catch (Exception e) {
            throw new Exception("Erro ao buscar pedido.");
        }
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity<BaseResponse> getOrder(@PathVariable UUID id) throws Exception {
        try {
            var optional = orderService.findById(id);
            if(optional.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("Pedido não encontrado."));
            }
            var orderResponse = responseUtil.generateEmployeeOrderResponse(optional.get());
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(orderResponse));
        } catch (Exception e) {
            throw new Exception("Erro ao buscar pedido.");
        }
    }

    @PostMapping("/pedido/{id}/orcamento")
    public ResponseEntity<BaseResponse> postBudget(@PathVariable UUID id, @RequestBody @Valid EmployeeBudgetDto budgetDto) throws Exception {
        try {
            var optional = orderService.findById(id);
            if(optional.isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("Pedido não encontrado."));
            }
            Order order = optional.get();
            //TODO adicionar orcamento
            return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(optional.get()));
        } catch (Exception e) {
            throw new Exception("Erro ao buscar pedido.");
        }
    }

//    @GetMapping("relatorio-fincanceiro")
//    public ResponseEntity<BaseResponse> financialReport(
//            @RequestParam FinancialReportType reportType,
//            @RequestParam(required = false) Date startDate,
//            @RequestParam(required = false) Date endDate)
//        throws Exception {
//        try {
//            switch (reportType){
//                case TIME_BASED:
//                    if(startDate == null || endDate == null){
//                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("Datas inválidas"));
//                    }
//                    List<Revenue> revenues = revenueService.findAllWithinDateRange(startDate, endDate);
//                    double timeTotal = revenueService.findSumWithinDateRange(startDate, endDate);
//
//                    List<FinancialReportRevenueDay> timeReports = revenueService.findDailySumWithinDateRange(startDate, endDate);
//                    for(FinancialReportRevenueDay report : timeReports){
//                        List<Revenue> dailyRevenues = revenues.stream()
//                                .filter(revenue -> isSameDay(report.getDay(), revenue.getDate()))
//                                .collect(Collectors.toList());
//                        report.setRevenues(dailyRevenues);
//                    }
//                    var timeResponse = new FinancialReportTimeResponse(timeTotal, timeReports);
//                    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(timeResponse));
//                case EQUIPMENT_CATEGORY:
//                    List<EquipmentCategory> equipmentCategories = equipmentCategoryService.findAll();
//                    List<FinancialReportEquipmentCategoryInfo> equipmentCategoryReports = new ArrayList<>();
//                    double equipmentCategoryTotal = 0;
//                    for (EquipmentCategory category : equipmentCategories) {
//                        double totalRevenue = revenueService.findSumWithEquipmentCategoryId(category.getId());
//                        String equipmentCategoryName = category.getName();
//                        equipmentCategoryReports.add(new FinancialReportEquipmentCategoryInfo(equipmentCategoryName, totalRevenue));
//                        equipmentCategoryTotal += totalRevenue;
//                    }
//                    var equipmentCategoryResponse = new FinancialReportEquipmentCategoryResponse(equipmentCategoryTotal, equipmentCategoryReports);
//                    return ResponseEntity.status(HttpStatus.OK).body(new BaseResponse(equipmentCategoryResponse));
//                default:
//                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse("nenhuma categoria especificada"));
//            }
//        } catch (Exception e) {
//            throw new Exception("Erro ao buscar pedido.");
//        }
//    }

    private boolean isSameDay(Date date1, Date date2) {
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(date1);
        cal2.setTime(date2);
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR);
    }
}

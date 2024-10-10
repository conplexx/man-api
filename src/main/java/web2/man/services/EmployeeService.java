package web2.man.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web2.man.models.entities.Employee;
import web2.man.repositories.EmployeeRepository;
import web2.man.repositories.EmployeeRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Transactional
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    @Transactional
    public void deleteById(UUID id) {
        employeeRepository.deleteById(id);
    }
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    public Optional<Employee> findById(UUID id) {
        return employeeRepository.findById(id);
    }
    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }
    public boolean existsById(UUID id) {
        return employeeRepository.existsById(id);
    }
}

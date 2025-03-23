package ru.hits.kt1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.kt1.dto.CreateEmployeeDto;
import ru.hits.kt1.models.Employee;
import ru.hits.kt1.services.EmployeeService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    public Employee createEmployee(@RequestBody CreateEmployeeDto employee) { return employeeService.createEmployee(employee); }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable String id) { return employeeService.getEmployeeById(id); }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable String id) { employeeService.deleteEmployee(id); }

    @GetMapping("/all")
    public List<Employee> getAllEmployees() { return employeeService.getAllEmployees(); }
}

package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.kt1.Enum.Position;
import ru.hits.kt1.dto.CreateEmployeeDto;
import ru.hits.kt1.models.Employee;
import ru.hits.kt1.repository.EmployeeRepository;

import java.util.UUID;

@AllArgsConstructor
@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(CreateEmployeeDto DTO) {
        if (DTO.getEmployeeName() == null || DTO.getStatus() == null) {
            throw new IllegalArgumentException("Name and status can't be null");
        }

        Employee employee = new Employee();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        employee.setId(uuid);

        employee.setEmployeeName(DTO.getEmployeeName());
        employee.setStatus(DTO.getStatus());
        employee.setPosition(DTO.getPosition() != null ? DTO.getPosition() : Position.UNDEFINED);

        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(String id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
}

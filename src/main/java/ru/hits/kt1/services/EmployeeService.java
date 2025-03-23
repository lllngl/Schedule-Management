package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.kt1.Enum.Position;
import ru.hits.kt1.Enum.Status;
import ru.hits.kt1.dto.CreateEmployeeDto;
import ru.hits.kt1.exceptions.NotFoundException;
import ru.hits.kt1.exceptions.ValidationException;
import ru.hits.kt1.models.Employee;
import ru.hits.kt1.repository.EmployeeRepository;

import java.util.UUID;

@AllArgsConstructor
@Service
public class EmployeeService {
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(CreateEmployeeDto DTO) {
        if (DTO.getEmployeeName() == null || DTO.getStatus() == null) {
            throw new ValidationException("Name and status can't be null");
        }

        if (DTO.getStatus() != Status.WORKING && DTO.getStatus() != Status.DISMISSED && DTO.getStatus() != Status.TRIAL
                && DTO.getStatus() != Status.TIME_OFF) {
            throw new ValidationException("This status doesn't exist");
        }

        if (DTO.getPosition() != Position.EMPLOYEE && DTO.getPosition() != Position.MANAGER &&
                DTO.getPosition() != Position.TECH && DTO.getPosition() != Position.UNDEFINED && DTO.getPosition() != null) {
            throw new ValidationException("This position doesn't exist");
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
                .orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    public void deleteEmployee(String id) {
        if (!employeeRepository.existsById(id)) { throw new NotFoundException("Employee not found"); }
        employeeRepository.deleteById(id);
    }
}

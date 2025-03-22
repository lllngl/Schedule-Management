package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.kt1.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}

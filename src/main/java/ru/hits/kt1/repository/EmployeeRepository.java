package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.kt1.models.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}

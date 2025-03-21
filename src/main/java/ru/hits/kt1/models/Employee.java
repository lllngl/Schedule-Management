package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import ru.hits.kt1.Enum.Position;
import ru.hits.kt1.Enum.Status;

@Data
@Entity
@Table(name = "employee")
public class Employee {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, length = 32)
    private String id;
    @Column(name="employee_name", nullable = false)
    private String employeeName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Position position;


    @PrePersist
    @PreUpdate
    public void setDefaultPosition() {
        if (this.position == null) {
            this.position = Position.UNDEFINED;
        }
    }
}

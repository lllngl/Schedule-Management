package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @Column(updatable = false, length = 32)
    private String id;
    @Column(name = "schedule_name")
    private String scheduleName;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "update_date", nullable = false)
    private LocalDateTime updateDate;
}

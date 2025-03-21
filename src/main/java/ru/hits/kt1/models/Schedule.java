package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "schedule")
public class Schedule {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, length = 32)
    private String id;
    @Column(name = "schedule_name")
    private String scheduleName;
    @Column(name = "creation_date", nullable = false)
    private LocalDate creationDate;
    @Column(name = "update_date", nullable = false)
    private LocalDate updateDate;
}

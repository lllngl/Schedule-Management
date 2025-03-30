package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.*;

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

    @OneToMany(mappedBy = "schedule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<SchedulePeriod> periods = new ArrayList<>();
}
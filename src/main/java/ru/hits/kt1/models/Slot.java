package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetTime;

@Data
@Entity
@Table(name = "slot")
public class Slot {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, length = 32)
    private String id;
    @Column(name = "schedule_template_id", nullable = false, length = 32)
    private String scheduleTemplateId;
    @Column(name = "begin_time", nullable = false)
    private OffsetTime beginTime;
    @Column(name = "end_time", nullable = false)
    private OffsetTime endTime;
}

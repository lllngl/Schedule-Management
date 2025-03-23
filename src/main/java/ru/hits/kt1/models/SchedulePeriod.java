package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;
import ru.hits.kt1.Enum.SlotType;

import java.time.OffsetTime;

@Data
@Entity
@Table(name = "schedule_period")
public class SchedulePeriod {
    @Id
    @Column(updatable = false, length = 32)
    private String id;
    @Column(name = "slot_id", nullable = false, length = 32)
    private String slotId;
    @Column(name = "schedule_id", nullable = false, length = 32)
    private String scheduleId;
    @Enumerated(EnumType.STRING)
    @Column(name = "slot_type", nullable = false, length = 20)
    private SlotType slotType;
    @Column(name = "administrator_id", nullable = false, length = 32)
    private String administratorId;
    @Column(name = "executor_id", length = 32)
    private String executorId;
    @Column(name = "begin_time", nullable = false)
    private OffsetTime beginTime;
}

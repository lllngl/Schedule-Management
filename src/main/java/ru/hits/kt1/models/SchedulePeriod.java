package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;
import ru.hits.kt1.Enum.SlotType;

@Data
@Entity
@Table(name = "schedule_period")
@EqualsAndHashCode(exclude = {"schedule", "slot", "administrator", "executor"})
@ToString(exclude = {"schedule", "slot", "administrator", "executor"})
public class SchedulePeriod {
    @Id
    @Column(updatable = false, length = 32)
    private String id;

    @OneToOne
    @JoinColumn(name = "slot_id", nullable = false, unique = true)
    private Slot slot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @Enumerated(EnumType.STRING)
    @Column(name = "slot_type", nullable = false, length = 20)
    private SlotType slotType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "administrator_id", nullable = false)
    private Employee administrator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor_id")
    private Employee executor;
}

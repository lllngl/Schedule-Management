package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetTime;

@Data
@Entity
@Table(name = "slot")
@EqualsAndHashCode(exclude = {"template", "schedulePeriod"})
@ToString(exclude = {"template", "schedulePeriod"})
public class Slot {
    @Id
    @Column(updatable = false, length = 32)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_template_id", nullable = false)
    private Template template;

    @OneToOne(mappedBy = "slot")
    private SchedulePeriod schedulePeriod;

    @Column(name = "begin_time", nullable = false)
    private OffsetTime beginTime;

    @Column(name = "end_time", nullable = false)
    private OffsetTime endTime;
}
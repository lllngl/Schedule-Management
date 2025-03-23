package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.kt1.models.SchedulePeriod;

import java.util.List;

public interface SchedulePeriodRepository extends JpaRepository<SchedulePeriod, String> {
    List<SchedulePeriod> findByScheduleIdOrderByBeginTime(String scheduleId);
}

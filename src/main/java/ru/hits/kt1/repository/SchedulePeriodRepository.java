package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.hits.kt1.models.SchedulePeriod;

import java.util.List;

public interface SchedulePeriodRepository extends JpaRepository<SchedulePeriod, String>, JpaSpecificationExecutor<SchedulePeriod> {
    List<SchedulePeriod> findByScheduleIdOrderByBeginTime(String scheduleId);
    List<SchedulePeriod> findAllByScheduleId(String scheduleId);
}

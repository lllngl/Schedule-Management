package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.kt1.model.SchedulePeriod;

public interface SchedulePeriodRepository extends JpaRepository<SchedulePeriod, String> {
}

package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hits.kt1.model.Schedule;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, String> {
}

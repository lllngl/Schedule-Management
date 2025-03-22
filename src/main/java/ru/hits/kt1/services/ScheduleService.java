package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.kt1.dto.CreateScheduleDto;
import ru.hits.kt1.models.Schedule;
import ru.hits.kt1.repository.ScheduleRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;

    public Schedule createSchedule(CreateScheduleDto DTO) {
        if (DTO.getScheduleName() == null) {
            throw new IllegalArgumentException("scheduleName can't be null");
        }

        Schedule schedule = new Schedule();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        schedule.setId(uuid);

        schedule.setScheduleName(DTO.getScheduleName());
        var date = LocalDateTime.now();
        schedule.setCreationDate(date);
        schedule.setUpdateDate(date);

        return scheduleRepository.save(schedule);
    }

    public Schedule getScheduleById(String id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
    }
}
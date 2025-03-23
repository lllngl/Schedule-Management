package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.kt1.dto.CreateScheduleDto;
import ru.hits.kt1.dto.FullScheduleDto;
import ru.hits.kt1.dto.PeriodDto;
import ru.hits.kt1.models.Schedule;
import ru.hits.kt1.models.SchedulePeriod;
import ru.hits.kt1.repository.SchedulePeriodRepository;
import ru.hits.kt1.repository.ScheduleRepository;
import ru.hits.kt1.repository.SlotRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private SchedulePeriodRepository schedulePeriodRepository;
    private SlotRepository slotRepository;

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


    public FullScheduleDto getFullSchedule(String id, String name) {
        if (id == null && name == null) {
            throw new IllegalArgumentException("Id or name can't be null");
        }

        Schedule schedule;

        if (id != null && name != null) {
            Schedule scheduleById = scheduleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Schedule with that id not found"));
            Schedule scheduleByName = scheduleRepository.findByScheduleName(name)
                    .orElseThrow(() -> new RuntimeException("Schedule with that name not found"));

            if (scheduleByName == null || !scheduleById.getId().equals(scheduleByName.getId())) {
                throw new IllegalArgumentException("Id and name unmatched");
            }
            schedule = scheduleById;

        } else if (id != null) {
            schedule = scheduleRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Schedule with that id not found"));
        } else {
            schedule = scheduleRepository.findByScheduleName(name)
                    .orElseThrow(() -> new RuntimeException("Schedule with that name not found"));
        }

        FullScheduleDto fullScheduleDto = new FullScheduleDto();
        fullScheduleDto.setId(schedule.getId());
        fullScheduleDto.setScheduleName(schedule.getScheduleName());
        fullScheduleDto.setCreationDate(schedule.getCreationDate());
        fullScheduleDto.setUpdateDate(schedule.getUpdateDate());

        List<PeriodDto> periods = schedulePeriodRepository.findByScheduleIdOrderByBeginTime(schedule.getId())
                .stream()
                .map(this::mapToPeriodDto)
                .collect(Collectors.toList());

        fullScheduleDto.setPeriods(periods);
        return fullScheduleDto;
    }

    private PeriodDto mapToPeriodDto(SchedulePeriod period) {
        PeriodDto dto = new PeriodDto();

        dto.setId(period.getId());
        dto.setSlotId(period.getSlotId());
        dto.setScheduleId(period.getScheduleId());
        dto.setSlotType(period.getSlotType());
        dto.setAdministratorId(period.getAdministratorId());
        dto.setExecutorId(period.getExecutorId());
        dto.setBeginTime(period.getBeginTime());

        return dto;
    }
}
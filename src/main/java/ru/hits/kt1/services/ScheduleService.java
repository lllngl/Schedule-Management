package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.kt1.dto.CreateScheduleDto;
import ru.hits.kt1.dto.FullScheduleDto;
import ru.hits.kt1.dto.PeriodDto;
import ru.hits.kt1.exceptions.NotFoundException;
import ru.hits.kt1.exceptions.ValidationException;
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

    public Schedule createSchedule(CreateScheduleDto DTO) {
        if (DTO.getScheduleName() == null) {
            throw new ValidationException("scheduleName can't be null");
        }

        Schedule schedule = new Schedule();
        schedule.setId(UUID.randomUUID().toString().replace("-", ""));
        schedule.setScheduleName(DTO.getScheduleName());

        LocalDateTime now = LocalDateTime.now();
        schedule.setCreationDate(now);
        schedule.setUpdateDate(now);

        return scheduleRepository.save(schedule);
    }



    public Schedule getScheduleById(String id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));
    }



    public FullScheduleDto getFullSchedule(String id, String name) {
        if (id == null && name == null) {
            throw new ValidationException("Both id and name can't be null");
        }

        Schedule schedule;

        if (id != null && name != null) {
            Schedule scheduleById = scheduleRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Schedule with that id not found"));
            Schedule scheduleByName = scheduleRepository.findByScheduleName(name)
                    .orElseThrow(() -> new NotFoundException("Schedule with that name not found"));

            if (scheduleByName == null || !scheduleById.getId().equals(scheduleByName.getId())) {
                throw new ValidationException("Schedule id and name unmatched");
            }
            schedule = scheduleById;

        } else if (id != null) {
            schedule = scheduleRepository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Schedule with that id not found"));
        } else {
            schedule = scheduleRepository.findByScheduleName(name)
                    .orElseThrow(() -> new NotFoundException("Schedule with that name not found"));
        }

        FullScheduleDto fullScheduleDto = new FullScheduleDto();
        fullScheduleDto.setId(schedule.getId());
        fullScheduleDto.setScheduleName(schedule.getScheduleName());
        fullScheduleDto.setCreationDate(schedule.getCreationDate());
        fullScheduleDto.setUpdateDate(schedule.getUpdateDate());

        List<PeriodDto> periods = schedule.getPeriods().stream()
                .sorted(Comparator.comparing(period -> period.getSlot().getBeginTime()))
                .map(this::mapToPeriodDto)
                .collect(Collectors.toList());

        fullScheduleDto.setPeriods(periods);
        return fullScheduleDto;
    }

    private PeriodDto mapToPeriodDto(SchedulePeriod period) {
        PeriodDto dto = new PeriodDto();

        dto.setId(period.getId());
        dto.setSlotId(period.getSlot().getId());
        dto.setScheduleId(period.getSchedule().getId());
        dto.setSlotType(period.getSlotType());
        dto.setAdministratorId(period.getAdministrator().getId());
        dto.setExecutorId(period.getExecutor().getId());
        dto.setBeginTime(period.getSlot().getBeginTime());

        return dto;
    }



    public void deleteSchedule(String id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        scheduleRepository.delete(schedule);
    }



    public List<Schedule> getAllSchedules() { return scheduleRepository.findAll(); }
}
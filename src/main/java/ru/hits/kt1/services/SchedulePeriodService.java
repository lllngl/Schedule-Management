package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.hits.kt1.Enum.SlotType;
import ru.hits.kt1.Specification.PeriodFiltration;
import ru.hits.kt1.dto.CreateSchedulePeriodDto;
import ru.hits.kt1.dto.FilterDto;
import ru.hits.kt1.dto.SortDto;
import ru.hits.kt1.models.Employee;
import ru.hits.kt1.models.Schedule;
import ru.hits.kt1.models.SchedulePeriod;
import ru.hits.kt1.models.Slot;
import ru.hits.kt1.repository.EmployeeRepository;
import ru.hits.kt1.repository.SchedulePeriodRepository;
import ru.hits.kt1.repository.ScheduleRepository;
import ru.hits.kt1.repository.SlotRepository;

import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SchedulePeriodService {
    private SchedulePeriodRepository schedulePeriodRepository;
    private ScheduleRepository scheduleRepository;
    private SlotRepository slotRepository;
    private EmployeeRepository employeeRepository;

    public SchedulePeriod createSchedulePeriod(CreateSchedulePeriodDto DTO, String administratorId) {
        if (DTO.getSlotId() == null || DTO.getScheduleId() == null) {
            throw new IllegalArgumentException("Fields can't be null");
        }

        Optional<Schedule> existingSchedule = scheduleRepository.findById(DTO.getScheduleId());
        if (existingSchedule.isEmpty()) {
            throw new RuntimeException("Schedule not found");
        }

        Optional<Slot> existingSlot = slotRepository.findById(DTO.getSlotId());
        if (existingSlot.isEmpty()) {
            throw new RuntimeException("Slot not found");
        }

        Optional<Employee> existingEmployee = employeeRepository.findById(administratorId);
        if (existingEmployee.isEmpty()) {
            throw new RuntimeException("Administrator not found");
        }

        OffsetTime beginTime = existingSlot.get().getBeginTime();
        OffsetTime endTime = existingSlot.get().getEndTime();

        List<SchedulePeriod> allPeriods = schedulePeriodRepository.findAllByScheduleId(DTO.getScheduleId());

        for (SchedulePeriod period : allPeriods) {
            if (period.getBeginTime().isBefore(endTime) && period.getEndTime().isAfter(beginTime)) {
                throw new RuntimeException("The new period overlaps with existing periods");
            }
        }

        SchedulePeriod schedulePeriod = new SchedulePeriod();

        if (DTO.getExecutorId() != null) {
            Optional<Employee> existingExecutor = employeeRepository.findById(DTO.getExecutorId());
            if (existingExecutor.isEmpty()) {
                throw new RuntimeException("Executor not found");
            }
            schedulePeriod.setExecutorId(DTO.getExecutorId());
        }

        if(DTO.getExecutorId() == null) {
            schedulePeriod.setExecutorId(administratorId);
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");
        schedulePeriod.setId(uuid);

        schedulePeriod.setScheduleId(DTO.getScheduleId());
        schedulePeriod.setSlotId(DTO.getSlotId());
        schedulePeriod.setBeginTime(beginTime);
        schedulePeriod.setEndTime(endTime);
        schedulePeriod.setAdministratorId(administratorId);
        schedulePeriod.setSlotType(DTO.getSlotType() != null ? DTO.getSlotType() : SlotType.UNDEFINED);

        return schedulePeriodRepository.save(schedulePeriod);
    }

    public SchedulePeriod getSchedulePeriod(String id) {
        return schedulePeriodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule period not found"));
    }

    public List<SchedulePeriod> getAllPeriods(FilterDto filter, SortDto sort, int page, int size) {
        Specification<SchedulePeriod> spec = Specification
                .where(PeriodFiltration.withId(filter.getId()))
                .and(PeriodFiltration.withSlotId(filter.getSlotId()))
                .and(PeriodFiltration.withScheduleId(filter.getScheduleId()))
                .and(PeriodFiltration.withSlotType(filter.getSlotType()))
                .and(PeriodFiltration.withAdministratorId(filter.getAdministratorId()))
                .and(PeriodFiltration.withExecutorId(filter.getExecutorId()));

        Sort.Direction direction = Sort.Direction.fromString(sort.getDirection());
        Sort sortObj = Sort.by(direction, sort.getField());

        Pageable pageable = PageRequest.of(page, size, sortObj);

        Page<SchedulePeriod> periodsPage = schedulePeriodRepository.findAll(spec, pageable);

        return periodsPage.getContent();
    }
}

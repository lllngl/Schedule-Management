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
import ru.hits.kt1.exceptions.NotFoundException;
import ru.hits.kt1.exceptions.ValidationException;
import ru.hits.kt1.models.Employee;
import ru.hits.kt1.models.Schedule;
import ru.hits.kt1.models.SchedulePeriod;
import ru.hits.kt1.models.Slot;
import ru.hits.kt1.repository.EmployeeRepository;
import ru.hits.kt1.repository.SchedulePeriodRepository;
import ru.hits.kt1.repository.ScheduleRepository;
import ru.hits.kt1.repository.SlotRepository;

import java.util.List;
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
            throw new ValidationException("Fields can't be null");
        }

        Schedule schedule = scheduleRepository.findById(DTO.getScheduleId())
                .orElseThrow(() -> new NotFoundException("Schedule not found"));

        Slot slot = slotRepository.findById(DTO.getSlotId())
                .orElseThrow(() -> new NotFoundException("Slot not found"));

        Employee administrator = employeeRepository.findById(administratorId)
                .orElseThrow(() -> new NotFoundException("Administrator not found"));

        if (DTO.getSlotType() != SlotType.LOCAL && DTO.getSlotType() != SlotType.FROM_HOME &&
                DTO.getSlotType() != SlotType.UNDEFINED && DTO.getSlotType() != null) {
            throw new ValidationException("This slot type doesn't exist");
        }

        List<SchedulePeriod> allPeriods = schedulePeriodRepository.findAllByScheduleId(DTO.getScheduleId());

        for (SchedulePeriod period : allPeriods) {
            if (period.getSlot().getBeginTime().isBefore(slot.getEndTime())
                    && period.getSlot().getEndTime().isAfter(slot.getBeginTime())) {
                throw new ValidationException("The new period overlaps with existing periods");
            }
        }

        SchedulePeriod period = new SchedulePeriod();
        period.setId(UUID.randomUUID().toString().replace("-", ""));
        period.setSchedule(schedule);
        period.setSlot(slot);
        period.setAdministrator(administrator);
        period.setSlotType(DTO.getSlotType() != null ? DTO.getSlotType() : SlotType.UNDEFINED);

        if (DTO.getExecutorId() != null) {
            Employee executor = employeeRepository.findById(DTO.getExecutorId())
                    .orElseThrow(() -> new NotFoundException("Executor not found"));
            period.setExecutor(executor);
        } else {
            period.setExecutor(administrator);
        }

        return schedulePeriodRepository.save(period);
    }



    public SchedulePeriod getSchedulePeriod(String id) {
        return schedulePeriodRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Schedule period not found"));
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



    public void deletePeriod(String id) {
        if (!schedulePeriodRepository.existsById(id)) { throw new NotFoundException("Schedule period not found"); }
        schedulePeriodRepository.deleteById(id);
    }
}

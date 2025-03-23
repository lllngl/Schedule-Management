package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.kt1.Enum.SlotType;
import ru.hits.kt1.dto.CreateSchedulePeriodDto;
import ru.hits.kt1.models.Employee;
import ru.hits.kt1.models.Schedule;
import ru.hits.kt1.models.SchedulePeriod;
import ru.hits.kt1.models.Slot;
import ru.hits.kt1.repository.EmployeeRepository;
import ru.hits.kt1.repository.SchedulePeriodRepository;
import ru.hits.kt1.repository.ScheduleRepository;
import ru.hits.kt1.repository.SlotRepository;

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
        schedulePeriod.setBeginTime(existingSlot.get().getBeginTime());
        schedulePeriod.setAdministratorId(administratorId);
        schedulePeriod.setSlotType(DTO.getSlotType() != null ? DTO.getSlotType() : SlotType.UNDEFINED);

        return schedulePeriodRepository.save(schedulePeriod);
    }

    public SchedulePeriod getSchedulePeriod(String id) {
        return schedulePeriodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule period not found"));
    }
}

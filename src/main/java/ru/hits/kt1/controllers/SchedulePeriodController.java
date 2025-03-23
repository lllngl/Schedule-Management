package ru.hits.kt1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.kt1.Enum.SlotType;
import ru.hits.kt1.dto.CreateSchedulePeriodDto;
import ru.hits.kt1.dto.FilterDto;
import ru.hits.kt1.dto.SortDto;
import ru.hits.kt1.models.SchedulePeriod;
import ru.hits.kt1.services.SchedulePeriodService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/schedulePeriod")
public class SchedulePeriodController {
    private final SchedulePeriodService schedulePeriodService;

    @PostMapping
    public SchedulePeriod createSchedulePeriod(@RequestBody CreateSchedulePeriodDto schedulePeriod,
                                               @RequestHeader("x-current-user") String administratorId) {
        return schedulePeriodService.createSchedulePeriod(schedulePeriod, administratorId);
    }

    @GetMapping("/{id}")
    public SchedulePeriod getSchedulePeriodById(@PathVariable String id) {
        return schedulePeriodService.getSchedulePeriod(id);
    }

    @GetMapping
    public List<SchedulePeriod> getAllPeriods(@RequestParam(required = false) String id,
                                              @RequestParam(required = false) String slotId,
                                              @RequestParam(required = false) String scheduleId,
                                              @RequestParam(required = false) SlotType slotType,
                                              @RequestParam(required = false) String administratorId,
                                              @RequestParam(required = false) String executorId,
                                              @RequestParam(defaultValue = "beginTime") String sortField,
                                              @RequestParam(defaultValue = "ASC") String sortDirection,
                                              @RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "3") int size) {

        FilterDto filter = new FilterDto();
        filter.setId(id);
        filter.setSlotId(slotId);
        filter.setScheduleId(scheduleId);
        filter.setSlotType(slotType);
        filter.setAdministratorId(administratorId);
        filter.setExecutorId(executorId);

        SortDto sort = new SortDto();
        sort.setField(sortField);
        sort.setDirection(sortDirection);

        if (page < 0) { page = 0;}
        if (size <= 0) { size = 3; }

        List<SchedulePeriod> periods = schedulePeriodService.getAllPeriods(filter, sort, page, size);

        return periods;
    }
}

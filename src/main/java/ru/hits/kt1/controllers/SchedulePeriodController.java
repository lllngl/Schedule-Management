package ru.hits.kt1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.kt1.dto.CreateSchedulePeriodDto;
import ru.hits.kt1.models.SchedulePeriod;
import ru.hits.kt1.services.SchedulePeriodService;

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
}

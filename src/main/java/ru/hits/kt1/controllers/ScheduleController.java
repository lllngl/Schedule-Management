package ru.hits.kt1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.kt1.dto.CreateScheduleDto;
import ru.hits.kt1.models.Schedule;
import ru.hits.kt1.services.ScheduleService;

@RestController
@AllArgsConstructor
@RequestMapping("/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping
    public Schedule createSchedule(@RequestBody CreateScheduleDto schedule) {
        return scheduleService.createSchedule(schedule);
    }

    @GetMapping("/{id}")
    public Schedule getScheduleById(@PathVariable String id) {
        return scheduleService.getScheduleById(id);
    }
}
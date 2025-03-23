package ru.hits.kt1.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class FullScheduleDto {
    private String id;
    private String scheduleName;
    private LocalDateTime creationDate;
    private LocalDateTime updateDate;
    private List<PeriodDto> periods;
}

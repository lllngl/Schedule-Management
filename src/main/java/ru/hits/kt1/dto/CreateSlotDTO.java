package ru.hits.kt1.dto;

import lombok.Data;

import java.time.OffsetTime;

@Data
public class CreateSlotDTO {
    private String scheduleTemplateId;
    private OffsetTime beginTime;
    private OffsetTime endTime;
}

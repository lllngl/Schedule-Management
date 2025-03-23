package ru.hits.kt1.dto;

import lombok.Data;
import ru.hits.kt1.Enum.SlotType;

@Data
public class FilterDto {
    private String id;
    private String slotId;
    private String scheduleId;
    private SlotType slotType;
    private String administratorId;
    private String executorId;
}

package ru.hits.kt1.dto;

import lombok.Data;
import ru.hits.kt1.Enum.Position;
import ru.hits.kt1.Enum.Status;

@Data
public class CreateEmployeeDto {
    private String employeeName;
    private Status status;
    private Position position;
}

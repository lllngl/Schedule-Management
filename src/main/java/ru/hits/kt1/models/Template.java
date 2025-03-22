package ru.hits.kt1.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "template")
public class Template {
    @Id
    @Column(updatable = false, length = 32)
    private String id;
    @Column(name = "creation_date", nullable = false)
    private LocalDateTime creationDate;
    @Column(name = "template_type", nullable = false, length = 2)
    private String templateType;
}

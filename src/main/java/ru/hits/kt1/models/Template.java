package ru.hits.kt1.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

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

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Slot> slots = new ArrayList<>();
}
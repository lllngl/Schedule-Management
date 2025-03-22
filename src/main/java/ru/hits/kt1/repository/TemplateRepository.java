package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.kt1.models.Template;

public interface TemplateRepository extends JpaRepository<Template, String> {
}

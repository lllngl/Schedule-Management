package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.kt1.model.Template;

public interface TemplateRepository extends JpaRepository<Template, String> {
}

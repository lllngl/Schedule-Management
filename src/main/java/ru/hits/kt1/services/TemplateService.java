package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.kt1.dto.CreateTemplateDto;
import ru.hits.kt1.exceptions.NotFoundException;
import ru.hits.kt1.exceptions.ValidationException;
import ru.hits.kt1.models.Template;
import ru.hits.kt1.repository.TemplateRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TemplateService {
    private TemplateRepository templateRepository;

    public Template createTemplate(CreateTemplateDto DTO) {
        if (DTO.getTemplateType() == null) {
            throw new ValidationException("templateType can't be null");
        }

        Template template = new Template();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        template.setId(uuid);

        template.setTemplateType(DTO.getTemplateType());
        template.setCreationDate(LocalDateTime.now());
        return templateRepository.save(template);
    }

    public Template getTemplateById(String id) {
        return templateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Template not found"));
    }

    public void deleteTemplate(String id) {
        if (!templateRepository.existsById(id)) { throw new NotFoundException("Template not found"); }
        templateRepository.deleteById(id);
    }
}

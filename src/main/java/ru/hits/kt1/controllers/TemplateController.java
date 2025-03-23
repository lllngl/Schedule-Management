package ru.hits.kt1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.kt1.dto.CreateTemplateDto;
import ru.hits.kt1.models.Template;
import ru.hits.kt1.services.TemplateService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/template")
public class TemplateController {
    private final TemplateService templateService;

    @PostMapping
    public Template createTemplate(@RequestBody CreateTemplateDto template) {
        return templateService.createTemplate(template);
    }

    @GetMapping("/{id}")
    public Template getTemplateById(@PathVariable String id) {
        return templateService.getTemplateById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteTemplate(@PathVariable String id) { templateService.deleteTemplate(id); }

    @GetMapping("/all")
    public List<Template> getAllTemplates() { return templateService.getAllTemplates(); }
}

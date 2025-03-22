package ru.hits.kt1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.kt1.dto.CreateTemplateDto;
import ru.hits.kt1.models.Template;
import ru.hits.kt1.services.TemplateService;

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
}

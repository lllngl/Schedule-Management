package ru.hits.kt1.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.hits.kt1.dto.CreateSlotDTO;
import ru.hits.kt1.exceptions.NotFoundException;
import ru.hits.kt1.exceptions.ValidationException;
import ru.hits.kt1.models.Slot;
import ru.hits.kt1.models.Template;
import ru.hits.kt1.repository.SlotRepository;
import ru.hits.kt1.repository.TemplateRepository;

import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SlotService {
    private SlotRepository slotRepository;
    private TemplateRepository templateRepository;

    public Slot createSlot(CreateSlotDTO DTO) {
        if (DTO.getScheduleTemplateId() == null || DTO.getBeginTime() == null || DTO.getEndTime() == null) {
            throw new ValidationException("Fields can't be null");
        }

        Optional<Template> existingTemplate = templateRepository.findById(DTO.getScheduleTemplateId());
        if (existingTemplate.isEmpty()) {
            throw new NotFoundException("Template not found");
        }

        Slot slot = new Slot();

        String uuid = UUID.randomUUID().toString().replace("-", "");
        slot.setId(uuid);

        slot.setScheduleTemplateId(DTO.getScheduleTemplateId());
        slot.setBeginTime(DTO.getBeginTime());
        slot.setEndTime(DTO.getEndTime());

        return slotRepository.save(slot);
    }

    public Slot getSlotById(String id) {
        return slotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Slot not found"));
    }

    public void deleteSlot(String id) {
        if (!slotRepository.existsById(id)) { throw new NotFoundException("Slot not found"); }
        slotRepository.deleteById(id);
    }
}

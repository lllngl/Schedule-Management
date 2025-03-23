package ru.hits.kt1.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.hits.kt1.dto.CreateSlotDTO;
import ru.hits.kt1.models.Slot;
import ru.hits.kt1.services.SlotService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/slot")
public class SlotController {
    private final SlotService slotService;

    @PostMapping
    public Slot createSlot(@RequestBody CreateSlotDTO slot) {
        return slotService.createSlot(slot);
    }

    @GetMapping("/{id}")
    public Slot getSlotById(@PathVariable String id) {
        return slotService.getSlotById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteSlot(@PathVariable String id) { slotService.deleteSlot(id); }

    @GetMapping("/all")
    public List<Slot> getAllSlots() { return slotService.getAllSlots(); }
}

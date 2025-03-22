package ru.hits.kt1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hits.kt1.model.Slot;

public interface SlotRepository extends JpaRepository<Slot, String> {
}

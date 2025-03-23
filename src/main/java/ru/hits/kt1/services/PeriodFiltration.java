package ru.hits.kt1.services;

import org.springframework.data.jpa.domain.Specification;
import ru.hits.kt1.Enum.SlotType;
import ru.hits.kt1.models.SchedulePeriod;

public class PeriodFiltration {
    public static Specification<SchedulePeriod> withId(String id) {
        return (root, query, criteriaBuilder) ->
                id == null ? null : criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<SchedulePeriod> withSlotId(String slotId) {
        return (root, query, criteriaBuilder) ->
                slotId == null ? null : criteriaBuilder.equal(root.get("slotId"), slotId);
    }

    public static Specification<SchedulePeriod> withScheduleId(String scheduleId) {
        return (root, query, criteriaBuilder) ->
                scheduleId == null ? null : criteriaBuilder.equal(root.get("scheduleId"), scheduleId);
    }

    public static Specification<SchedulePeriod> withSlotType(SlotType slotType) {
        return (root, query, criteriaBuilder) ->
                slotType == null ? null : criteriaBuilder.equal(root.get("slotType"), slotType);
    }

    public static Specification<SchedulePeriod> withAdministratorId(String administratorId) {
        return (root, query, criteriaBuilder) ->
                administratorId == null ? null : criteriaBuilder.equal(root.get("administratorId"), administratorId);
    }

    public static Specification<SchedulePeriod> withExecutorId(String executorId) {
        return (root, query, criteriaBuilder) ->
                executorId == null ? null : criteriaBuilder.equal(root.get("executorId"), executorId);
    }
}

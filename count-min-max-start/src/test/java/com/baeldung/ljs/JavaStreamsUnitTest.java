package com.baeldung.ljs;

import com.baeldung.ljs.domain.model.Task;
import com.baeldung.ljs.domain.model.TaskStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JavaStreamsUnitTest {
    private final Collection<Task> tasks = List.of(
            new Task("T1", "John's house construction", "Construction of John's house in LA", LocalDate.of(2024, 1, 1), TaskStatus.IN_PROGRESS),
            new Task("T2", "Thomas High School reparation", "Reparation of Thomas High School in London", LocalDate.of(2024, 8, 20), TaskStatus.DONE),
            new Task("T3", "Flower Cafe construction", "Construction of Flower Cafe in Bucharest", LocalDate.of(2025, 6, 30), TaskStatus.IN_PROGRESS),
            new Task("T4", "Lily's house construction", "Construction of Lily's house in NY", LocalDate.of(2028, 11, 15), TaskStatus.DONE),
            new Task("T5", "Bee Steak House restoration", "Restoration of Bee Steak House in Constanta", LocalDate.of(2032, 9, 25), TaskStatus.ON_HOLD),
            new Task("T6", "West Outer Ring street construction", "Construction of West Outer Ring street in Hamburg", LocalDate.of(2035, 5, 18), TaskStatus.IN_PROGRESS),
            new Task("T7", "Green river bridge restoration", "Restoration of Green river bridge in Dublin", LocalDate.of(2029, 2, 22), TaskStatus.ON_HOLD),
            new Task("T8", "Jane's Jacket factory reparation", "Reparation of Jane's Jacket factory", LocalDate.of(2028, 6, 10), TaskStatus.IN_PROGRESS));

    @Test
    void whenCountingAllTasks_thenReturnsCorrectCount() {
        long taskCount = tasks.stream().count();

        assertEquals(8L, taskCount);
    }

    @Test
    void whenCountingDoneTasks_thenReturnsCorrectCount() {
        long doneTaskCount = tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.DONE)
                .count();

        assertEquals(2L, doneTaskCount);
    }

    @Test
    void whenFindingDoneTaskWithLatestDueDate_thenReturnsCorrectTask() {
        Optional<Task> latestDone = tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.DONE)
                .max(Comparator.comparing(Task::getDueDate));

        assertTrue(latestDone.isPresent());
        assertEquals("T4", latestDone.get().getCode());
    }

    @Test
    void whenFindingInProgressTaskWithEarliestDueDate_thenReturnsCorrectTask() {
        Optional<Task> earliestInProgress = tasks.stream()
                .filter(t -> t.getStatus() == TaskStatus.IN_PROGRESS)
                .min(Comparator.comparing(Task::getDueDate));

        assertTrue(earliestInProgress.isPresent());
        assertEquals("T1", earliestInProgress.get().getCode());
    }

    @Test
    void whenImplementingComparable_thenUseNaturalOrderingWithMin() {
        Optional<Task> earliestTask = tasks.stream().min(Comparator.naturalOrder());
        assertTrue(earliestTask.isPresent());
        assertEquals("T1", earliestTask.get().getCode());
    }

    @Test
    void whenImplementingComparable_thenUseNaturalOrderingWithMax() {
        Optional<Task> latestTask = tasks.stream().max(Comparator.naturalOrder());
        assertTrue(latestTask.isPresent());
        assertEquals("T6", latestTask.get().getCode());
    }
}
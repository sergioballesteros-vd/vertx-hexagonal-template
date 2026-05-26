package com.portfolio;

import com.portfolio.application.TaskService;
import com.portfolio.domain.TaskItem;
import com.portfolio.infrastructure.repository.InMemoryTaskRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskServiceTest {
    @Test
    void createTask_shouldPersistTask() {
        TaskService taskService = new TaskService(new InMemoryTaskRepository());

        TaskItem task = taskService.createTask("Build portfolio service");

        assertEquals("Build portfolio service", task.title());
        assertEquals(1, taskService.listTasks().size());
    }

    @Test
    void createTask_shouldFailWhenTitleIsBlank() {
        TaskService taskService = new TaskService(new InMemoryTaskRepository());

        assertThrows(IllegalArgumentException.class, () -> taskService.createTask("   "));
    }
}

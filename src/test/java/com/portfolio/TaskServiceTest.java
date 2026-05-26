package com.portfolio;

import com.portfolio.application.TaskService;
import com.portfolio.infrastructure.repository.InMemoryTaskRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TaskServiceTest {
    @Test
    void createTask_shouldPersistTask() {
        TaskService taskService = new TaskService(new InMemoryTaskRepository());

        var task = taskService.createTask("Build portfolio service").blockingGet();

        assertEquals("Build portfolio service", task.title());
        assertEquals(1, taskService.listTasks().blockingGet().size());
    }

    @Test
    void createTask_shouldFailWhenTitleIsBlank() {
        TaskService taskService = new TaskService(new InMemoryTaskRepository());

        assertThrows(RuntimeException.class, () -> taskService.createTask("   ").blockingGet());
    }
}

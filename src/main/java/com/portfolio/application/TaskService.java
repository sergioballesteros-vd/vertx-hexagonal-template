package com.portfolio.application;

import com.portfolio.domain.TaskItem;
import com.portfolio.domain.TaskRepository;

import java.util.List;

public final class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public TaskItem createTask(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }

        TaskItem taskItem = TaskItem.create(title.trim());
        return taskRepository.save(taskItem);
    }

    public List<TaskItem> listTasks() {
        return taskRepository.findAll();
    }
}

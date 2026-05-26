package com.portfolio.application;

import com.portfolio.domain.TaskItem;
import com.portfolio.domain.TaskRepository;
import io.reactivex.Single;

import java.util.List;

public final class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Single<TaskItem> createTask(String title) {
        if (title == null || title.isBlank()) {
            return Single.error(new IllegalArgumentException("title is required"));
        }

        TaskItem taskItem = TaskItem.create(title.trim());
        return taskRepository.save(taskItem);
    }

    public Single<List<TaskItem>> listTasks() {
        return taskRepository.findAll();
    }
}

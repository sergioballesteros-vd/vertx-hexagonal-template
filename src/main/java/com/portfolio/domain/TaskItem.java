package com.portfolio.domain;

import java.util.Objects;
import java.util.UUID;

public final class TaskItem {
    private final String taskId;
    private final String title;

    public TaskItem(String taskId, String title) {
        this.taskId = Objects.requireNonNull(taskId, "taskId is required");
        this.title = Objects.requireNonNull(title, "title is required");
    }

    public static TaskItem create(String title) {
        return new TaskItem(UUID.randomUUID().toString(), title);
    }

    public String taskId() {
        return taskId;
    }

    public String title() {
        return title;
    }
}

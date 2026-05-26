package com.portfolio.domain;

import java.util.UUID;

public record TaskItem(String taskId, String title) {
    public static TaskItem create(String title) {
        return new TaskItem(UUID.randomUUID().toString(), title);
    }
}

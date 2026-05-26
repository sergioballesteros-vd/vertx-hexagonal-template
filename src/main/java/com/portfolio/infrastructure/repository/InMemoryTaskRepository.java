package com.portfolio.infrastructure.repository;

import com.portfolio.domain.TaskItem;
import com.portfolio.domain.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class InMemoryTaskRepository implements TaskRepository {
    private final List<TaskItem> store = new CopyOnWriteArrayList<>();

    @Override
    public TaskItem save(TaskItem taskItem) {
        store.add(taskItem);
        return taskItem;
    }

    @Override
    public List<TaskItem> findAll() {
        return new ArrayList<>(store);
    }
}

package com.portfolio.infrastructure.repository;

import com.portfolio.domain.TaskItem;
import com.portfolio.domain.TaskRepository;
import io.reactivex.Single;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public final class InMemoryTaskRepository implements TaskRepository {
    private final List<TaskItem> store = new CopyOnWriteArrayList<>();

    @Override
    public Single<TaskItem> save(TaskItem taskItem) {
        store.add(taskItem);
        return Single.just(taskItem);
    }

    @Override
    public Single<List<TaskItem>> findAll() {
        return Single.just(new ArrayList<>(store));
    }
}

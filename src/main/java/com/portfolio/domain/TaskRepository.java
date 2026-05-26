package com.portfolio.domain;

import io.reactivex.Single;

import java.util.List;

public interface TaskRepository {
    Single<TaskItem> save(TaskItem taskItem);

    Single<List<TaskItem>> findAll();
}

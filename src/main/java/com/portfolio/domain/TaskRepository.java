package com.portfolio.domain;

import java.util.List;

public interface TaskRepository {
    TaskItem save(TaskItem taskItem);

    List<TaskItem> findAll();
}

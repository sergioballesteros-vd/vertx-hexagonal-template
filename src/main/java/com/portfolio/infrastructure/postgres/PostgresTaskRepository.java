package com.portfolio.infrastructure.postgres;

import com.portfolio.domain.TaskItem;
import com.portfolio.domain.TaskRepository;
import io.reactivex.Single;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.Tuple;

import java.util.ArrayList;
import java.util.List;

public final class PostgresTaskRepository implements TaskRepository {
    private final PgPool pgPool;

    public PostgresTaskRepository(PgPool pgPool) {
        this.pgPool = pgPool;
    }

    @Override
    public Single<TaskItem> save(TaskItem taskItem) {
        return pgPool.preparedQuery("INSERT INTO tasks(task_id, title) VALUES ($1, $2)")
            .rxExecute(Tuple.of(taskItem.taskId(), taskItem.title()))
            .map(rows -> taskItem);
    }

    @Override
    public Single<List<TaskItem>> findAll() {
        return pgPool.query("SELECT task_id, title FROM tasks ORDER BY title")
            .rxExecute()
            .map(rows -> {
                List<TaskItem> tasks = new ArrayList<>();
                for (Row row : rows) {
                    tasks.add(new TaskItem(row.getString("task_id"), row.getString("title")));
                }
                return tasks;
            });
    }
}

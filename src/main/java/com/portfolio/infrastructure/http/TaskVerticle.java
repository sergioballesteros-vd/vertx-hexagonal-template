package com.portfolio.infrastructure.http;

import com.portfolio.application.TaskService;
import com.portfolio.domain.TaskItem;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

import java.util.List;

public final class TaskVerticle extends AbstractVerticle {
    private final TaskService taskService;

    public TaskVerticle(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void start(Promise<Void> startPromise) {
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/health").handler(ctx ->
            ctx.response().putHeader("content-type", "application/json")
                .end(new JsonObject().put("status", "ok").encode())
        );

        router.post("/tasks").handler(ctx -> {
            JsonObject body = ctx.body().asJsonObject();
            String title = body == null ? null : body.getString("title");

            taskService.createTask(title).subscribe(
                taskItem -> ctx.response().setStatusCode(201).putHeader("content-type", "application/json")
                    .end(new JsonObject().put("task_id", taskItem.taskId()).put("title", taskItem.title()).encode()),
                error -> ctx.response().setStatusCode(400).putHeader("content-type", "application/json")
                    .end(new JsonObject().put("error", error.getMessage()).encode())
            );
        });

        router.get("/tasks").handler(ctx ->
            taskService.listTasks().subscribe(
                tasks -> ctx.response().putHeader("content-type", "application/json")
                    .end(toJson(tasks).encode()),
                error -> ctx.response().setStatusCode(500).putHeader("content-type", "application/json")
                    .end(new JsonObject().put("error", error.getMessage()).encode())
            )
        );

        vertx.createHttpServer().requestHandler(router).listen(8080)
            .onSuccess(server -> startPromise.complete())
            .onFailure(startPromise::fail);
    }

    private JsonArray toJson(List<TaskItem> tasks) {
        JsonArray array = new JsonArray();
        for (TaskItem task : tasks) {
            array.add(new JsonObject().put("task_id", task.taskId()).put("title", task.title()));
        }
        return array;
    }
}

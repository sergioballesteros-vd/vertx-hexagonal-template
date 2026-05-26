package com.portfolio.infrastructure.http;

import com.portfolio.application.TaskService;
import com.portfolio.domain.TaskItem;
import com.portfolio.infrastructure.repository.InMemoryTaskRepository;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public final class Main {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        TaskService taskService = new TaskService(new InMemoryTaskRepository());

        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        router.get("/health").handler(ctx ->
            ctx.response()
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("status", "ok").encode())
        );

        router.post("/tasks").handler(ctx -> {
            try {
                JsonObject body = ctx.body().asJsonObject();
                String title = body.getString("title");
                TaskItem taskItem = taskService.createTask(title);

                ctx.response()
                    .setStatusCode(201)
                    .putHeader("content-type", "application/json")
                    .end(new JsonObject()
                        .put("task_id", taskItem.taskId())
                        .put("title", taskItem.title())
                        .encode());
            } catch (IllegalArgumentException badRequest) {
                ctx.response()
                    .setStatusCode(400)
                    .putHeader("content-type", "application/json")
                    .end(new JsonObject().put("error", badRequest.getMessage()).encode());
            }
        });

        router.get("/tasks").handler(ctx -> {
            JsonArray tasks = new JsonArray();
            for (TaskItem taskItem : taskService.listTasks()) {
                tasks.add(new JsonObject()
                    .put("task_id", taskItem.taskId())
                    .put("title", taskItem.title()));
            }

            ctx.response()
                .putHeader("content-type", "application/json")
                .end(tasks.encode());
        });

        vertx.createHttpServer().requestHandler(router).listen(8080);
    }
}

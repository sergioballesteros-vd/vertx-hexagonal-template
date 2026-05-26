package com.portfolio.infrastructure.http;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.portfolio.application.TaskService;
import com.portfolio.infrastructure.di.AppModule;
import io.vertx.reactivex.core.Vertx;

public final class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new AppModule());
        Vertx vertx = injector.getInstance(Vertx.class);
        TaskService taskService = injector.getInstance(TaskService.class);

        vertx.getDelegate().deployVerticle(new TaskVerticle(taskService));
    }
}

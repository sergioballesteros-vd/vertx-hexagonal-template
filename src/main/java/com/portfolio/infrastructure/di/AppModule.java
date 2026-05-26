package com.portfolio.infrastructure.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.portfolio.application.TaskService;
import com.portfolio.domain.TaskRepository;
import com.portfolio.infrastructure.postgres.PostgresTaskRepository;
import com.portfolio.infrastructure.repository.InMemoryTaskRepository;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.pgclient.PgPool;
import io.vertx.sqlclient.PoolOptions;

public final class AppModule extends AbstractModule {
    @Override
    protected void configure() {
    }

    @Provides
    @Singleton
    Vertx provideVertx() {
        return Vertx.vertx();
    }

    @Provides
    @Singleton
    PgPool providePgPool(Vertx vertx) {
        PgConnectOptions connectOptions = new PgConnectOptions()
            .setPort(Integer.parseInt(System.getenv().getOrDefault("PG_PORT", "5432")))
            .setHost(System.getenv().getOrDefault("PG_HOST", "localhost"))
            .setDatabase(System.getenv().getOrDefault("PG_DATABASE", "portfolio"))
            .setUser(System.getenv().getOrDefault("PG_USER", "postgres"))
            .setPassword(System.getenv().getOrDefault("PG_PASSWORD", "postgres"));

        return PgPool.pool(vertx, connectOptions, new PoolOptions().setMaxSize(5));
    }

    @Provides
    @Singleton
    TaskRepository provideTaskRepository(PgPool pgPool) {
        boolean inMemoryMode = Boolean.parseBoolean(System.getenv().getOrDefault("IN_MEMORY_MODE", "true"));
        if (inMemoryMode) {
            return new InMemoryTaskRepository();
        }
        return new PostgresTaskRepository(pgPool);
    }

    @Provides
    @Singleton
    TaskService provideTaskService(TaskRepository taskRepository) {
        return new TaskService(taskRepository);
    }
}

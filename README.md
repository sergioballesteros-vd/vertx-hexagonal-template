# Vert.x Hexagonal Architecture Template ☕️🏗️

Template portfolio con una implementación E2E mínima y real de arquitectura hexagonal en Vert.x.

## Qué incluye
- `domain`: entidad `TaskItem` + puerto `TaskRepository`
- `application`: caso de uso `TaskService`
- `infrastructure`: adaptador HTTP Vert.x + adaptador repositorio in-memory

## Endpoints
- `GET /health`
- `POST /tasks` body `{"title":"..."}`
- `GET /tasks`

## Ejecutar
```bash
docker compose up --build
```
API: `http://localhost:8081`

## Tests
```bash
./gradlew test
```

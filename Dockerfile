FROM gradle:8.8-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean installDist --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/install/vertx-hexagonal-template /app
EXPOSE 8080
CMD ["/app/bin/vertx-hexagonal-template"]

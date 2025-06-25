# Etapa 1: Build
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew build -x test --no-daemon

# Etapa 2: Runtime
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

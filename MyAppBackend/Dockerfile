# Etapa 1: build
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN ./gradlew build --no-daemon


# Etapa 2: runtime
FROM amazoncorretto:17-alpine-jdk
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]

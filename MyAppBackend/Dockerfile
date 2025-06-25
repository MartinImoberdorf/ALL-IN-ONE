FROM gradle:8.5-jdk17-alpine AS build
COPY build/libs/MyAppBackend-0.0.1-SNAPSHOT.jar /app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
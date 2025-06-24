FROM amazoncorretto:17-alpine-jdk

WORKDIR /app

COPY build/libs/MyAppBackend-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

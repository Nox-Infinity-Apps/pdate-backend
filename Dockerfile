FROM maven:3.8.3-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN ["./gradlew", "clean", "bootJar"]
COPY build/libs/*.jar app.jar
EXPOSE 1234
ENTRYPOINT ["java", "-jar", "app.jar"]

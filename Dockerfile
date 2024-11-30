FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/*.jar app.jar

EXPOSE 1234

ENTRYPOINT ["java", "-jar", "app.jar"]

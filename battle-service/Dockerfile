FROM openjdk:17-alpine

LABEL authors="ValeryiaStrechyna"
LABEL version="0.0.1-SNAPSHOT"

COPY target/battle-service-0.0.1-SNAPSHOT.jar /app/battle-service.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
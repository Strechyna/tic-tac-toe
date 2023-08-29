FROM alpine/git as clone
WORKDIR /app
RUN git clone https://github.com/spring-projects/spring-petclinic.git

FROM maven:3.5-jdk-8-alpine as package
WORKDIR /app
COPY --from=clone /app/spring-petclinic /app
RUN mvn package

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=package /app/target/spring-petclinic-1.5.1.jar /app
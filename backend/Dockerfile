FROM maven:3.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
COPY settings.xml .
COPY src ./src

RUN mvn -B -s settings.xml clean package -DskipTests

FROM eclipse-temurin:21-jre

ENV LANG=en_US.UTF-8
ENV LANGUAGE=en_US:en
ENV LC_ALL=en_US.UTF-8

RUN mkdir -p /app
WORKDIR /app

COPY --from=builder /app/target/eduforge-training-platform-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar", "--spring.profiles.active=docker"]
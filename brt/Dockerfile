FROM openjdk:17.0.2-jdk-slim
ARG JAR_FILE=target/*.jar
WORKDIR /app
COPY ${JAR_FILE} app.jar
CMD ["java", "-jar", "/app/app.jar"]
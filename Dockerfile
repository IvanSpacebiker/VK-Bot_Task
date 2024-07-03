FROM gradle:jdk21-alpine
LABEL authors="ikazakov"
WORKDIR /app
COPY . .
RUN gradle clean assemble
ENTRYPOINT ["java", "-jar", "./build/libs/VK-Bot-Task-0.0.1-SNAPSHOT.jar"]

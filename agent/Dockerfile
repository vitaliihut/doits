FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/agent.jar /app/agent.jar

COPY src/main/resources/ /app/resources/

CMD ["java", "-jar", "agent.jar"]

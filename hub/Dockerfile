FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/hub.jar /app/hub.jar

COPY src/main/resources/ /app/resources/

CMD ["java", "-jar", "hub.jar"]

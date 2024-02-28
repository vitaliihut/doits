FROM openjdk:17-alpine

WORKDIR /app

COPY build/libs/store.jar /app/store.jar

COPY src/main/resources/ /app/resources/

CMD ["java", "-jar", "store.jar"]

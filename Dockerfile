FROM eclipse-temurin:21-jdk-alpine

EXPOSE 8080

ARG VERSION=1.0.0

COPY build/libs/tictactoe-${VERSION}.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
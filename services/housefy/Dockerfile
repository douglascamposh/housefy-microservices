FROM --platform=linux/amd64 eclipse-temurin:17.0.8_7-jdk-focal
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

ENV AWS_ACCESS_KEY_ID=${AWS_ACCESS_KEY_ID}
ENV AWS_SECRET_ACCESS_KEY=${AWS_SECRET_ACCESS_KEY}

ENTRYPOINT ["java","-jar","/app.jar"]

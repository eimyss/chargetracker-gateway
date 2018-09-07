FROM openjdk:8-jdk-alpine
VOLUME /tmp
ENV stage prod
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-Dspring.profiles.active=${stage}","-jar","/app.jar"]


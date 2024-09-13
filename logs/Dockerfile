FROM openjdk:17
COPY ./target/logs-0.0.1-SNAPSHOT.jar /usr/src/logs/
WORKDIR /usr/src/logs
EXPOSE 8080
CMD ["java", "-jar", "logs-0.0.1-SNAPSHOT.jar"]
FROM openjdk:17
COPY ./target/logs-0.0.1-SNAPSHOT.jar /usr/src/logs/
COPY opentelemetry-javaagent.jar /usr/src/logs/
WORKDIR /usr/src/logs
EXPOSE 8080
CMD ["java", "-javaagent:opentelemetry-javaagent.jar","-jar", "logs-0.0.1-SNAPSHOT.jar"]
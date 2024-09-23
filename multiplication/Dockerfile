FROM openjdk:17
COPY ./target/multiplication-0.0.1-SNAPSHOT.jar /usr/src/multiplication/
COPY opentelemetry-javaagent.jar /usr/src/multiplication/
WORKDIR /usr/src/multiplication
EXPOSE 8080
CMD ["java", "-javaagent:opentelemetry-javaagent.jar","-jar", "multiplication-0.0.1-SNAPSHOT.jar"]
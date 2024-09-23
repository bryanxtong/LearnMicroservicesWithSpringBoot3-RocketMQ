FROM openjdk:17
COPY ./target/gamification-0.0.1-SNAPSHOT.jar /usr/src/gamification/
COPY opentelemetry-javaagent.jar /usr/src/gamification/
WORKDIR /usr/src/gamification
EXPOSE 8080
CMD ["java", "-javaagent:opentelemetry-javaagent.jar","-jar", "gamification-0.0.1-SNAPSHOT.jar"]
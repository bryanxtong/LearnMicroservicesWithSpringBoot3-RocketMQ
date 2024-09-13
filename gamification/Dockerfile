FROM openjdk:17
COPY ./target/gamification-0.0.1-SNAPSHOT.jar /usr/src/gamification/
WORKDIR /usr/src/gamification
EXPOSE 8080
CMD ["java", "-jar", "gamification-0.0.1-SNAPSHOT.jar"]
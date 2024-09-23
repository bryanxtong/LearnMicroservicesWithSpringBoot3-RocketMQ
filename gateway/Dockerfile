FROM openjdk:17
COPY ./target/gateway-0.0.1-SNAPSHOT.jar /usr/src/gateway/
COPY opentelemetry-javaagent.jar /usr/src/gateway/
WORKDIR /usr/src/gateway
EXPOSE 8000
CMD ["java", "-javaagent:opentelemetry-javaagent.jar","-jar", "gateway-0.0.1-SNAPSHOT.jar"]
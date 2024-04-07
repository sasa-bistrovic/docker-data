FROM maven:3.9.6-sapmachine-21 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY react-crud-kafka-build /workspace/react-crud-kafka-build
ENV SPRING_KAFKA_URL=kafka:9092 \
    SPRING_KAFKA_TOPIC=item-topic
RUN mvn -f pom.xml clean package

FROM openjdk:21-jdk
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
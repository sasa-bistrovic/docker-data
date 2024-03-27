FROM maven:3.9.6-sapmachine-21 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY react-crud-rabbitmq-build /workspace/react-crud-rabbitmq-build
ENV SPRING_RABBITMQ_HOST=rabbitmq \
    SPRING_RABBITMQ_PORT=15672 \
    SPRING_RABBITMQ_USERNAME=guest \
    SPRING_RABBITMQ_PASSWORD=guest
RUN mvn -f pom.xml clean package

FROM openjdk:21-jdk
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java","-jar","app.jar"]
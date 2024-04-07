FROM maven:3.9.6-sapmachine-21 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY react-crud-elasticsearch-build /workspace/react-crud-elasticsearch-build
ENV SPRING_ELASTICSEARCH_URL=elasticsearch:9200
RUN mvn -f pom.xml clean package -DskipTests -Dspring-boot.run.skip=true

FROM openjdk:21-jdk
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","app.jar"]
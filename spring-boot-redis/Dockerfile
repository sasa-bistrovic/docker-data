FROM maven:3.9.6-sapmachine-21 AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
COPY react-crud-redis-build /workspace/react-crud-redis-build
ENV SPRING_REDIS_HOST=redis \
    SPRING_REDIS_PORT=6379 \
    SPRING_REDIS_PASSWORD=redis
RUN mvn -f pom.xml clean package -DskipTests -Dspring-boot.run.skip=true

FROM openjdk:21-jdk
COPY --from=build /workspace/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","app.jar"]
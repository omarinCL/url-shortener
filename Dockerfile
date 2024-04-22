FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN ./mvnw install -DskipTests

FROM eclipse-temurin:21-jdk-alpine
VOLUME /tmp
ARG BUILD_WORKSPACE=/workspace/app
COPY --from=build ${BUILD_WORKSPACE}/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar", "--server.port=8080"]
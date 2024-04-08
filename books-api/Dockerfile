FROM maven:3.9.6-amazoncorretto-21 AS build
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=build ./build/target/*.jar ./app.jar
ENTRYPOINT ["java","-jar","app.jar"]
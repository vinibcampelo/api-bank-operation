FROM openjdk:17-jdk-slim AS build

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/bank_operation?serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=pismo
ENV SPRING_DATASOURCE_PASSWORD=pismopass

FROM openjdk:17-jdk-slim
WORKDIR demo
COPY --from=build target/*.jar demo.jar
CMD ["sh", "-c", "dockerize -wait tcp://mysql:3306 -timeout 30m && java -jar demo.jar"]

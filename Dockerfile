FROM openjdk:17-jdk-slim AS build

RUN apt-get update && apt-get install -y wget
RUN wget https://github.com/jwilder/dockerize/releases/download/v0.6.1/dockerize-linux-amd64-v0.6.1.tar.gz \
    && tar -C /usr/local/bin -xzvf dockerize-linux-amd64-v0.6.1.tar.gz \
    && rm dockerize-linux-amd64-v0.6.1.tar.gz

COPY pom.xml mvnw ./
COPY .mvn .mvn
RUN ./mvnw dependency:resolve

COPY src src
RUN ./mvnw package

ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/bank_operation?serverTimezone=UTC
ENV SPRING_DATASOURCE_USERNAME=pismo
ENV SPRING_DATASOURCE_PASSWORD=pismopass

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build target/*.jar api-bank-operation.jar
COPY --from=build /usr/local/bin/dockerize /usr/local/bin/dockerize

CMD ["sh", "-c", "dockerize -wait tcp://mysql:3306 -timeout 30m && java -jar api-bank-operation.jar"]

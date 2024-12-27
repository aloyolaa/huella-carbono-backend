FROM maven:3.9.5-amazoncorretto-17 AS build
WORKDIR /app
COPY . /app
RUN mvn clean package

FROM amazoncorretto:17 AS prod
EXPOSE 8080
COPY --from=build /app/target/huella-carbono-backend-0.0.1-SNAPSHOT.jar /app/huella-carbono-backend-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/app/huella-carbono-backend-0.0.1-SNAPSHOT.jar"]
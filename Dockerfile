# Todo update to confront monorepo

FROM maven:3.6.0-jdk-13-alpine as maven

COPY ./pom.xml ./pom.xml
RUN mvn dependency:go-offline -B

COPY ./src ./src
RUN mvn clean install -DskipTests

# FROM openjdk:13-jre-alpine
FROM openjdk:16-alpine3.13
EXPOSE 8080
COPY --from=maven target/blog-api-0.0.1-SNAPSHOT.jar ./
CMD ["java", "-jar","./blog-api-0.0.1-SNAPSHOT.jar"]
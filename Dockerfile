FROM maven:3.9-eclipse-temurin-21 AS builder
WORKDIR /.
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21
COPY --from=builder /target/joined-table-inheritance.jar joined-table-inheritance.jar
#COPY /target/joined-table-inheritance.jar joined-table-inheritance.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/joined-table-inheritance.jar"]
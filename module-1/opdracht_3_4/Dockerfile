FROM maven:3-eclipse-temurin-17-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY ./src src
RUN mvn clean install -DskipTests
RUN java -Djarmode=layertools -jar target/*.jar extract --destination target/extracted


FROM eclipse-temurin:17-jre-alpine AS runtime
WORKDIR /app
COPY --from=builder app/target/extracted/dependencies/ ./
COPY --from=builder app/target/extracted/spring-boot-loader/ ./
COPY --from=builder app/target/extracted/snapshot-dependencies/ ./
COPY --from=builder app/target/extracted/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]
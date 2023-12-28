FROM maven:3.9.3-eclipse-temurin-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM eclipse-temurin:11-jre
EXPOSE 8443:8443
EXPOSE 8446:8446
RUN mkdir /app
COPY --from=build /home/app/target/*.jar /app/student-service.jar
COPY config/config.yml /app/config.yml
ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/student-service.jar", "server", "/app/config.yml"]
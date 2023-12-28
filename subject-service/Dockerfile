FROM gradle:7-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle buildFatJar --no-daemon

FROM eclipse-temurin:11-jre
EXPOSE 8008:8008
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/subject-service.jar
ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/subject-service.jar"]
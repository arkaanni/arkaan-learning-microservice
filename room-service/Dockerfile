# FROM eclipse-temurin:17-jre
# EXPOSE 8080:8080
# RUN mkdir /app
# # Assuming the jar file is already built
# COPY build/libs/room-service-0.0.1.jar /app/room-service.jar
# ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/room-service.jar"]

FROM gradle:8.7-jdk17-graal as builder

WORKDIR /build

COPY src /build/src
COPY build.gradle gradle.properties settings.gradle /build/

RUN gradle nativeCompile --no-daemon

FROM debian:stable-slim

WORKDIR /app

COPY --from=builder /build/build/native/nativeCompile/room-service /app

EXPOSE 8080:8080

ENTRYPOINT ["/app/room-service"]
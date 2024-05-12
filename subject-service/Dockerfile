# FROM eclipse-temurin:17-jre-alpine
# EXPOSE 8443:8443
# RUN mkdir /app
# # Assuming the jar file is already built
# COPY /build/libs/subject-service-0.0.1-all.jar /app/subject-service.jar
# ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/subject-service.jar"]

FROM gradle:8.7-jdk17-graal as builder

WORKDIR /build

COPY src /build/src
COPY build.gradle gradle.properties settings.gradle /build/

RUN gradle nativeCompile --no-daemon

FROM debian:stable-slim

WORKDIR /app

COPY --from=builder /build/build/native/nativeCompile/subject-service /app

EXPOSE 8443:8443

ENTRYPOINT ["/app/subject-service"]
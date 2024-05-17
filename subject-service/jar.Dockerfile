FROM eclipse-temurin:17-jre-alpine
EXPOSE 8443:8443
RUN mkdir /app
# Assuming the jar file is already built
COPY /build/libs/subject-service-0.0.1-all.jar /app/subject-service.jar
ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/subject-service.jar"]
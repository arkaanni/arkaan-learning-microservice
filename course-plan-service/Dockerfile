FROM eclipse-temurin:11-jre
EXPOSE 8443:8443
EXPOSE 8446:8446
RUN mkdir /app
# Assuming the jar file is already built
COPY target/course-plan-service-0.0.jar /app/course-plan-service.jar
COPY config.yml /app/config.yml
ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/course-plan-service.jar", "server", "/app/config.yml"]
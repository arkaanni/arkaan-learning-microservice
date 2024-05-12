FROM eclipse-temurin:17-jre
EXPOSE 8443:8443
WORKDIR /app
# Assuming the jar file is already built
COPY target/course-plan-service.jar /app/course-plan-service.jar
ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/course-plan-service.jar"]
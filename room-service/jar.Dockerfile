FROM eclipse-temurin:17-jre
EXPOSE 8080:8080
RUN mkdir /app
# Assuming the jar file is already built
COPY build/libs/room-service-0.0.1.jar /app/room-service.jar
ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/room-service.jar"]
FROM eclipse-temurin:17-jre
EXPOSE 8080:8080
RUN mkdir /app
# Assuming the jar file is already built
COPY build/libs/*.jar /app/room-service.jar
ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/room-service.jar"]
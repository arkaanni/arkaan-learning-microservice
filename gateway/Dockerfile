FROM eclipse-temurin:17-jre
EXPOSE 8080:8080
WORKDIR /app
# Assuming the jar file is already built
COPY target/gateway.jar /app/gateway.jar
ENTRYPOINT ["java", "-Xmx64m", "-jar","/app/gateway.jar"]
# Use lightweight Java 17 image
FROM eclipse-temurin:17-jdk-alpine

# Create app directory
WORKDIR /app

# Copy jar file
COPY target/wallet-app-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

# Run application
ENTRYPOINT ["java","-jar","/app/app.jar"]
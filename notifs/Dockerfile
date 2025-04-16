# Use OpenJDK base image
FROM openjdk:23-jdk-slim

# Create a directory inside container
WORKDIR /app

# Add the jar file to the container
COPY target/notifs-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/json/ ./json/

# Expose the port (optional but useful for local runs)
EXPOSE 8082

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]

# Use OpenJDK base image
FROM openjdk:23-jdk-slim

# Create a directory inside container
WORKDIR /app

# Add the jar file to the container
COPY target/spring-boot-security-jwt-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (optional but useful for local runs)
EXPOSE 8081

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]

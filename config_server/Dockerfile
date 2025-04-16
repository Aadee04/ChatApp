# Use OpenJDK base image
FROM openjdk:23-jdk-slim

RUN apt-get update && apt-get install -y curl && rm -rf /var/lib/apt/lists/*

# Create a directory inside container
WORKDIR /app

# Add the jar file to the container
COPY target/config_server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port (optional but useful for local runs)
EXPOSE 8888

# Command to run the app
ENTRYPOINT ["java", "-jar", "app.jar"]

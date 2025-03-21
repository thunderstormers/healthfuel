# Use an official OpenJDK base image
FROM openjdk:24-ea-21-slim

# Set working directory
WORKDIR /app

# Install dependencies required for ONNX Runtime
RUN apt-get update && apt-get install -y \
    libstdc++6 \
    libc6 \
    && rm -rf /var/lib/apt/lists/*

# Copy the Spring Boot JAR file into the container
COPY target/healthfuel-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

ARG MODEL_PATH="classes/models/model.onnx"

RUN jar xf app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
# COPY docker-entrypoint.sh .
# RUN chmod +x docker-entrypoint.sh

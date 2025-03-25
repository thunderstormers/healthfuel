# Use an official OpenJDK base image
FROM openjdk:24-ea-21-slim

# Set working directory
WORKDIR /healthfuel

# Install dependencies required for ONNX Runtime
RUN apt-get update && apt-get install -y \
    libstdc++6 \
    libc6 \
    && rm -rf /var/lib/apt/lists/*

# Copy entrypoint script into the container
COPY docker-entrypoint.sh /docker-entrypoint.sh
RUN chmod 777 /docker-entrypoint.sh

# Expose the application port
EXPOSE 8080

ARG MODEL_PATH="BOOT-INF/classes/models/model.onnx"

# Define entrypoint
ENTRYPOINT ["/bin/bash", "/docker-entrypoint.sh"]

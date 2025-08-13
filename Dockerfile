# Use a multi-stage build to create the final image
# Stage 1: Build the application using Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder

# Set the working directory
WORKDIR /app

# Copy the pom.xml file to download dependencies
COPY pom.xml .

# Download all dependencies
RUN mvn dependency:go-offline

# Copy the rest of the source code
COPY src ./src

# Use a build-time argument for the encryption key
ARG ENCRYPTION_KEY
ENV ENCRYPTION_KEY=${ENCRYPTION_KEY}

# Build the application, skipping tests
RUN --mount=type=secret,id=encryption_key \
    mvn -Dencryption.key=$(cat /run/secrets/encryption_key) clean install -DskipTests

# Stage 2: Create the final image
FROM khipu/openjdk21-alpine

# Set environment variables from run-dev.sh
ENV EN_PRINT=y
ENV EN_LOG=y
ENV EN_MODE=development
ENV EN_SHOWSQL=true
ENV EN_DATA_INIT=true
ENV EN_TESTING=y
ENV EN_SMTP=n
# The ENCRYPTION_KEY should be passed at runtime for security
# e.g., docker run -e ENCRYPTION_KEY="your_secret_key" your-image-name

# Set the working directory
WORKDIR /app

# Copy the JAR file from the builder stage
COPY --from=builder /app/target/*.jar app.jar

# Expose the port the application runs on
EXPOSE 8081

# Command to run the application
ENTRYPOINT ["java","-jar","app.jar"]

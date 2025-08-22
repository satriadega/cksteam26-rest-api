# Use a multi-stage build to reduce the image size
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

ENV EN_PRINT=y
ENV EN_LOG=y
ENV EN_MODE=development
ENV EN_SHOWSQL=true
ENV EN_DATA_INIT=true
ENV EN_TESTING=y
ENV EN_SMTP=n

RUN --mount=type=secret,id=encryption_key \
    mvn -Dencryption.key=$(cat /run/secrets/encryption_key) clean install -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy all environment variables needed by Spring
ENV EN_PRINT=y
ENV EN_LOG=y
ENV EN_MODE=development
ENV EN_SHOWSQL=true
ENV EN_DATA_INIT=true
ENV EN_TESTING=y
ENV EN_SMTP=n

# Database credentials (bisa di-override saat docker run)
ENV SPRING_DATASOURCE_URL=""
ENV SPRING_DATASOURCE_USERNAME=""
ENV SPRING_DATASOURCE_PASSWORD=""

COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

# Build stage
FROM eclipse-temurin:17-jdk-alpine as builder

WORKDIR /app

# Copy gradle configuration and source code
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY gradle ./gradle
COPY gradlew .
COPY src ./src

# Build the application
RUN ./gradlew build -x test

# Runtime stage
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the built JAR from the builder stage
COPY --from=builder /app/build/libs/eshop-manager.jar eshop-manager.jar

# Expose port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=10s --start-period=5s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Run the application
ENTRYPOINT ["java", "-jar", "eshop.jar"]

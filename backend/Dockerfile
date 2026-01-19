# syntax=docker/dockerfile:1

# --- Build stage ---
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# Copy Maven wrapper and config first for dependency caching
COPY --link pom.xml mvnw ./
COPY --link .mvn .mvn/

# Make sure Maven wrapper is executable and download dependencies
RUN chmod +x mvnw && ./mvnw dependency:go-offline

# Copy source code
COPY --link src ./src/

# Build the application (skip tests for faster build)
RUN ./mvnw package -DskipTests

# --- Runtime stage ---
FROM eclipse-temurin:21-jre
WORKDIR /app

# Create a non-root user and group
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser

# Copy built jar from build stage
COPY --from=build /app/target/*.jar /app/app.jar

# Set permissions and switch to non-root user
RUN chown appuser:appgroup /app/app.jar
USER appuser

# Expose default Spring Boot port
EXPOSE 8080

# JVM container-aware flags for memory/resource management
ENV JAVA_OPTS="-XX:MaxRAMPercentage=80.0"

# Use exec form for proper signal handling
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]

# Use stable Java 21 runtime
FROM eclipse-temurin:21-jre

# Set working directory inside container
WORKDIR /app

# Copy built Spring Boot JAR from target folder
COPY target/*.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 9090

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]

# Set the base image
FROM openjdk:21-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the JAR file from the build directory to the container
COPY target/ecommerce-0.0.1-SNAPSHOT.jar app.jar

# Expose port 5080
EXPOSE 5080

# Run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "app.jar"]

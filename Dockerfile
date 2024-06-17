# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the Maven executable to the container
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file to the container
COPY pom.xml .

# Copy the application source to the container
COPY src src

# Install the application dependencies
RUN ./mvnw dependency:resolve

# Package the application
RUN ./mvnw package -DskipTests

# Expose port 8080
EXPOSE 8080

# Run the application
CMD ["java", "-jar", "target/dashboard-0.0.1-SNAPSHOT.jar"]

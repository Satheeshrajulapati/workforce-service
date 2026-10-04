# ==========================================
# Stage 1: Build the Spring Boot application
# ==========================================
FROM maven:3.9.11-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom first so Docker can cache dependencies
COPY pom.xml .

RUN mvn dependency:go-offline -B

# Copy application source
COPY src ./src

# Build executable Spring Boot JAR
RUN mvn clean package -DskipTests


# ==========================================
# Stage 2: Run the application
# ==========================================
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy only the generated JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Documentation of the application's container port.
# Render will supply PORT at runtime.
EXPOSE 8080

# Start Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
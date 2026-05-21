# Stage 1: Build the React frontend
FROM node:18-alpine AS frontend-builder
WORKDIR /app/frontend
COPY ["frontend/package.json", "frontend/package-lock.json", "./"]
RUN npm ci
COPY frontend/ ./
RUN npm run build

# Stage 2: Build the Spring Boot backend
FROM maven:3.8.5-openjdk-17 AS backend-builder
WORKDIR /app/backend

# Copy the pom.xml using JSON array syntax for paths with spaces
COPY ["java test case17-Spring Framework Integration/java test case16-JDBC Page/QuantityMeasurementApp-feature-UC15-NTierArchitecture/pom.xml", "./"]
RUN mvn dependency:go-offline -B

# Copy the backend source files
COPY ["java test case17-Spring Framework Integration/java test case16-JDBC Page/QuantityMeasurementApp-feature-UC15-NTierArchitecture/src", "./src"]

# Copy built React frontend assets into Spring Boot's static resources directory.
# This integrates the frontend and backend to run together on the same port!
COPY --from=frontend-builder /app/frontend/dist ./src/main/resources/static/

# Compile and package the executable JAR file
RUN mvn clean package -DskipTests

# Stage 3: Production runtime environment
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the built jar from Stage 2
COPY --from=backend-builder /app/backend/target/*.jar app.jar

# Render injects the PORT env var; Spring Boot binds to this port dynamically
EXPOSE 8080

# Start Spring Boot application with the production profile enabled (uses file-based H2 database)
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]

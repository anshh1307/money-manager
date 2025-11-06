FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app

# Copy source code
COPY . .

# Build jar (skip tests to avoid env var issues)
RUN mvn clean package -DskipTests


# -----------------------------
# Stage 2: Run the JAR (your original config)
# -----------------------------
FROM eclipse-temurin:21-jre
WORKDIR /app

# Copy the JAR from the build stage and rename to your desired name
COPY --from=build /app/target/moneymanager-0.0.1-SNAPSHOT.jar moneymanager.jar

# Keep your exact exposed port
EXPOSE 9090

# Keep your original entrypoint
ENTRYPOINT ["java", "-jar", "moneymanager.jar"]

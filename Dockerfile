FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

#stage 2
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/moneymanager-0.0.1-SNAPSHOT.jar moneymanager.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "moneymanager.jar"]

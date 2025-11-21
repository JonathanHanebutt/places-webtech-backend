# --- Build Stage ---
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Projekt rein kopieren
COPY . .

# Gradle-Wrapper ausführbar machen
RUN chmod +x gradlew

# Spring Boot JAR bauen, Tests überspringen (vereinfacht & stabil)
RUN ./gradlew bootJar -x test --no-daemon

# --- Run Stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app

# Gebautes JAR aus dem Build-Container holen
COPY --from=build /app/build/libs/*.jar app.jar

# Spring Boot hört auf 8080 (Render routet das)
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]

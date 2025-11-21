# --- Build Stage ---
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Gradle ausführen
COPY . .
RUN chmod +x gradlew
RUN ./gradlew bootJar

# --- Run Stage ---
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/build/libs/*.jar app.jar

# Render expects the app to listen on PORT (default: 10000)
ENV PORT=10000
EXPOSE 10000

CMD ["java", "-jar", "app.jar"]

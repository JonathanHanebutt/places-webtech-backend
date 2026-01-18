
# Projekt (Berlin Places) — Readme
# Team Jonathan und Keanu

Kurz: Backend mit Spring Boot, JWT-Auth, Places und User-Favorites. Fürs Studium, schnell lokal testbar.

## Was macht das Projekt
- JWT-basierte Anmeldung
- Orte verwalten (lesen, liken)
- User-spezifische Favoriten (persistiert, wenn DB passt)
- Dev: H2 file-based möglich, Prod: PostgreSQL empfohlen

## Voraussetzungen
- Java 17+
- Gradle
- macOS (entwickelt mit macOS)
- Optional: Docker

## Schnellstart (lokal / dev)
1. Repo clonen:
2. Dev-Profile benutzen (empfohlen: file-basierte H2, damit Daten nach Neustart bleiben). Beispiel in `src/main/resources/application-dev.properties`:
3. App starten (Oder in IntelliJ das `dev`-Profile wählen und starten.)

## Production
- Nutzt eine echte DB (z.B. PostgreSQL). Setze Umgebungsvariablen oder `application-prod.properties`:
    - `spring.datasource.url`
    - `spring.datasource.username`
    - `spring.datasource.password`
    - `app.jwt.secret` (wichtig)
- Docker: JAR mit Prod-Profile starten:

## Wichtige Konfig-Keys
- `app.jwt.secret` — Secret für JWT (muss gesetzt sein)
- `app.jwt.expirationMinutes` — Ablaufzeit (Default: 120)
- `spring.jpa.hibernate.ddl-auto` — für Dev `update` empfohlen

## API (wichtigste Endpunkte)
- `POST /api/auth/register` — user erstellen (wenn vorhanden)
- `POST /api/auth/login` — login, liefert JWT
- `GET /api/places` — Orte abrufen
- `POST /api/places/{id}/like` — Like (falls implementiert)
- Favorites:
    - `POST /api/favorites/{placeId}` — Favorite hinzufügen
    - `DELETE /api/favorites/{placeId}` — Favorite entfernen
    - `GET /api/favorites` — Liste der Favoriten (vollständig)
    - `GET /api/favorites/ids` — nur IDs (für Frontend nützlich)
    - `GET /api/favorites/check/{placeId}` — prüfen ob favorisiert

Beispiel: Login + Lieblings-IDs holen

## Wichtiger Hinweis zu H2
- `jdbc:h2:mem:*` ist flüchtig — Daten gehen beim Neustart verloren.
- Für lokale Persistenz `jdbc:h2:file:./data/devdb` benutzen oder echte DB (Postgres).

## Tests
- Tests ausführen:

- Es gibt Unit-Tests für JWT, Favorite-Service etc.

## Fehlerbehebung (häufige Probleme)
- Favorites verschwinden nach Logout/Login:
    - Prüfe ob DB persistent ist (nicht H2 In-Memory).
    - Prüfe, ob das Frontend nach Login `GET /api/favorites/ids` aufruft.
    - Prüfe, ob `app.jwt.secret` konstant bleibt (wenn Secret sich ändert, sind alte Tokens ungültig).
- Auth-Fehler (403):
    - JWT-Header `Authorization: Bearer <token>` richtig setzen.
    - SecurityConfig prüfen, ob Endpoints authentifiziert sind.

## Noch was?
- Fürs Praktische: `application-dev.properties` anpassen, `app.jwt.secret` setzen, testen mit `dev` Profile.
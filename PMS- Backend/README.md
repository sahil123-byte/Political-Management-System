# Political Management System - Backend

Spring Boot 3.5.3 (Java 21) REST API backend for the Political Management System - handles states, districts, constituencies, booths, parties, members, voters, campaigns, events, complaints, feedback, notifications, users, and role-based access control (ADMIN / MANAGER / MEMBER).

## Tech Stack
- Java 21, Spring Boot 3.5.3
- Spring Security + JWT authentication
- Spring Data JPA + MySQL
- Swagger / OpenAPI docs

## Setup

1. Start MySQL locally (XAMPP/WAMP/standalone - default `root` user, blank password).
   The database `political_management_system` is auto-created on first run.
   If your MySQL credentials differ, update `src/main/resources/application-local.properties`.

2. Run:
   ```
   ./mvnw spring-boot:run
   ```
   (Windows: `mvnw.cmd spring-boot:run`)

3. API runs at `http://localhost:8080`. Swagger docs: `http://localhost:8080/swagger-ui.html`

## Default login (auto-seeded on first run)
- Email: `admin@gmail.com`
- Password: `123456`

## Uploaded files
Profile photos, party logos, etc. are stored in a local `uploads/` folder (auto-created, gitignored - not committed to source control).

## Frontend
Pairs with the React frontend in the companion `PMS` repo.

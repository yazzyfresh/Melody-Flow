# MelodyFlow Backend

MelodyFlow is a concert planning and budgeting application that helps users discover concerts, save events, organize their plans, and track expenses for their concert experiences.

## Tech Stack
- **Java** — Backend programming language
- **Spring Boot** — Backend framework for building REST APIs
- **Spring Data JPA / Hibernate** — Database interaction and entity management
- **MySQL** — Relational database
- **Maven** — Dependency management and project build tool

## Features
- User and role management
- Artist and concert information management
- Venue information management
- Saved concerts
- Concert planning
- Expense tracking and budgeting

## Project Structure
- `entity` — Defines database entities and their relationships.
- `repository` — Handles database access.
- `service` — Contains application business logic.
- `controller` — Exposes REST API endpoints.
- `exception` — Handles application errors.

## Getting Started

1. Install Java, Maven, and MySQL.
2. Create a MySQL database for MelodyFlow.
3. Configure your database connection in `application-local.properties` or your local application configuration.
4. Run the Spring Boot application from IntelliJ IDEA or using Maven:

   ```bash
   ./mvnw spring-boot:run
   ```

   On Windows, you can use:

   ```bash
   .\mvnw.cmd spring-boot:run
   ```

5. Use your API testing tool to test the available endpoints.

## Configuration

Database credentials should be stored in local configuration or environment variables and **must not be committed to GitHub**.

## Project Status

The backend is under active development. Additional API endpoints, validation, and application functionality are being implemented as development progresses.

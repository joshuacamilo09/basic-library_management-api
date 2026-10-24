# Library API

REST API for managing a library catalogue and book loans, built with **Java 21** and **Spring Boot 4**.

It covers the core of a typical CRUD backend done properly: layered architecture, DTOs, validation, pagination, versioned database migrations, consistent error responses and OpenAPI documentation.

## Features

- Create, read, update and delete books
- **Loan** and **return** books, with business rules (an unavailable book can't be loaned, an available one can't be returned → `409 Conflict`)
- List available books and active loans
- Pagination and sorting on every list endpoint (`?page=0&size=10&sort=title,asc`)
- Schema managed by **Flyway** migrations, validated by Hibernate at startup
- Centralised error handling with meaningful HTTP status codes
- Interactive API docs with Swagger UI

## Architecture

```
Controller  →  Service  →  Repository  →  PostgreSQL
   (HTTP)    (rules)     (Spring Data JPA)
     ▲            │
     └── DTOs ◀───┘     entities never leave the service layer
```

| Package | Responsibility |
|---|---|
| `Controller` | REST endpoints, request/response mapping |
| `Service` | Business rules (loan/return), entity ↔ DTO mapping |
| `Repository` | Spring Data JPA queries (`findAllByAvailableIsTrue`, …) |
| `DTO` | Request/response contracts and a generic paged response |
| `Exception` | `@RestControllerAdvice` that turns exceptions into JSON errors |
| `Config` | OpenAPI metadata |

## API

Base path: `/api/library`

| Method | Path | Description |
|---|---|---|
| `POST` | `/create` | Add a book |
| `GET` | `/list/all` | List books (paged) |
| `GET` | `/list/{id}` | Get a book |
| `PUT` | `/update/{id}` | Update a book |
| `DELETE` | `/delete/{id}` | Delete a book |
| `PATCH` | `/{id}/loan` | Loan a book |
| `PATCH` | `/{id}/return` | Return a book |
| `GET` | `/available/true` | Available books (paged) |
| `GET` | `/loans` | Books currently on loan (paged) |

Error format:

```json
{
  "timestamp": "2026-05-01T10:15:30",
  "status": 409,
  "error": "Conflict",
  "message": "You can't loan a unavailable book"
}
```

## Running locally

Requirements: Java 21 and Docker.

```bash
docker compose up -d     # PostgreSQL on localhost:5432 (db: library)
./mvnw spring-boot:run   # API on http://localhost:8080
```

Swagger UI: http://localhost:8080/swagger-ui.html

Configuration is read from environment variables, with local defaults:

| Variable | Default |
|---|---|
| `DB_URL` | `jdbc:postgresql://localhost:5432/library` |
| `DB_USERNAME` / `DB_PASSWORD` | `postgres` / `postgres` |
| `PORT` | `8080` |

Migrations live in `src/main/resources/db/migration` and run automatically on startup.

## Tech stack

Java 21 · Spring Boot 4 · Spring Web MVC · Spring Data JPA · Bean Validation · PostgreSQL · Flyway · springdoc-openapi · Lombok · Maven

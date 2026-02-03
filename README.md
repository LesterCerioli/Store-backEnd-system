# Store E-Commerce Backend

North American e-commerce backend system built with **Spring Boot 3**, **Java 21**, and **PostgreSQL**. REST APIs with Swagger (OpenAPI 3), layered architecture following SOLID principles, and Hibernate for data access.

## Requirements

- **JDK 21**
- **Maven 3.8+**
- **PostgreSQL 14+** (for production; H2 used in tests)

## Architecture

- **Domain**: Entities and repository contracts (interfaces).
- **Application**: Service contracts and implementations (business logic, Hibernate via repositories).
- **Repository**: JPA/Hibernate repository implementations.
- **API**: REST controllers, DTOs, exception handling, Swagger.

Principles: **Single Responsibility**, **Open/Closed**, **Dependency Injection** (constructor injection throughout).

## Project Structure

```
src/main/java/com/store/ecommerce/
├── StoreBackendApplication.java
├── api/           # Controllers, DTOs, mappers, exception handling, Swagger config
├── application/   # Service interfaces (contracts) and impl (business logic)
├── domain/        # Entities and repository interfaces
└── repository/    # JPA repositories and implementations

src/main/resources/
├── application.yml
└── db/migration/  # SQL schema (optional; can use scripts/ for manual run)

scripts/            # PostgreSQL init and table creation scripts
```

## Database Setup

1. Create database and user (optional, adjust for your environment):

```bash
createdb store_db
psql -U postgres -d store_db -c "CREATE USER store_user WITH PASSWORD 'store_password';"
psql -U postgres -d store_db -c "GRANT ALL PRIVILEGES ON DATABASE store_db TO store_user;"
psql -U postgres -d store_db -c "GRANT ALL ON SCHEMA public TO store_user;"
```

2. Create tables (choose one):

- **Option A – Manual script (recommended for first run):**

```bash
psql -U store_user -d store_db -f scripts/create-tables-postgres.sql
```

- **Option B – Let Hibernate create (dev only):** In `application.yml`, set:

```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create
```

Then switch back to `validate` for production.

## Running the Application

```bash
mvn spring-boot:run
```

- **API base:** `http://localhost:8080/api`
- **Swagger UI:** `http://localhost:8080/api/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/api/v3/api-docs`

## Running Tests

```bash
mvn test
```

- Unit tests: service layer with mocked repositories.
- Integration tests: REST controllers with in-memory H2 (profile `test`).

## API Endpoints (overview)

| Resource   | Base path           | Operations                    |
|-----------|----------------------|-------------------------------|
| Categories | `/api/v1/categories`  | CRUD                          |
| Customers  | `/api/v1/customers`  | CRUD                          |
| Products   | `/api/v1/products`   | CRUD, list by category        |
| Orders     | `/api/v1/orders`     | Create, list, get, update status, delete |
| Addresses  | `/api/v1/addresses`  | CRUD, list by customer        |


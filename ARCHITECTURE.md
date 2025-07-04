# E-Commerce API - System Architecture

## Overview

This document describes the architecture and key design decisions of the Spring Boot-based E-Commerce API.

## 1. High-Level Architecture

The system follows a clean, layered architecture with modular boundaries. It is designed for maintainability, testability, and scalability.


![Architecture Image](wsd.jpg)

Considering the long term development potential, the system workflow can be listed as follows.

```
+---------------------+          +-----------------------------+
|   Client            | <------> | RESTful API (Spring MVC)    |
+---------------------+          +-----------------------------+
                                               |
                                               v
+-----------------------------+   +----------------------------+
|   Controller Layer          |   | DTOs (Request/Response)    |
+-----------------------------+   +----------------------------+
              |                                |
              v                                v
+-----------------------------+   +----------------------------+
|   Service Layer (Business)  |---|    Redis Cache             |
+-----------------------------+   +----------------------------+
              |
              v
+-----------------------------+
| Repository Layer (JPA)      |
| Spring Data + PostgreSQL    |
+-----------------------------+
              |
              v
+-----------------------------+
| PostgreSQL (Dockerized)     |
+-----------------------------+

Optional Future Integrations:
+-----------------------------+
|           Kafka             |
+-----------------------------+
```

## 2. Technology Stack (With future enhancement consideration)

| Concern           | Technology                        |
|-------------------|-----------------------------------|
| Framework         | Spring Boot 3.2.2                 |
| Language          | Java 21                           |
| Database          | PostgreSQL (Dockerized)           |
| API Documentation | Swagger (Springdoc OpenAPI 2.5.0) |
| Testing           | JUnit 5, Mockito, Testcontainers  |
| Logging           | JSON logs, ready for ELK          |
| Containerization  | Docker, Docker Compose            |
| Caching           | Redis                             |
| Messaging         | Apache Kafka                      |

## 3. Core Domain Models

### Entities

* **Customer**: id, name, email
* **Product**: id, name, price
* **Sale**: id, product, customer, timestamp, quantity, totalPrice
* **Wishlist**: id, customer, product

### Relationships

* Customer 1\:N Sale
* Customer 1\:N Wishlist
* Product 1\:N Sale
* Product 1\:N Wishlist

## 4. API Endpoints (Planned)

| Method | Endpoint                                     | Description                         |
| ------ |----------------------------------------------| ----------------------------------- |
| GET    | /customers/{id}/wishlist                     | Return wishlist for a customer      |
| GET    | /sales/today/total                           | Return total sale amount for today  |
| GET    | /sales/max-sale-day?from=...\&to=...         | Return max sale day in a range      |
| GET    | /products/top-selling/all-time/by-amount     | Top 5 products by total sale amount |
| GET    | /products/top-selling/last-month/by-quantity | Top 5 products by number of sales   |

## 5. Project Structure

```
src-main-package/
├── api/              # API endpoints
├── dto/              # Data Transfer Objects
├── entity/           # Entities
├── repository/       # Spring Data Repositories
├── service/          # Business logic
├── config/           # Swagger, Logging, etc.
├── util/             # Utility helper classes
└── Application.java
```

## 6. Testing Strategy

| Layer             | Tool                         | Purpose                                |
| ----------------- | ---------------------------- | -------------------------------------- |
| Unit tests        | JUnit 5 + Mockito            | Service and controller logic           |
| Integration tests | Spring Boot + Testcontainers | Real DB testing with PostgreSQL Docker |
| API docs test     | Springdoc                    | Auto-generated Swagger UI              |

## 7. Infrastructure

### Docker Compose

```yaml
services:
  postgres:
    image: 'postgres:latest'
    environment:
      - 'POSTGRES_DB=ecommerce_db'
      - 'POSTGRES_PASSWORD=ecommerce_pass'
      - 'POSTGRES_USER=ecommerce_user'
    ports:
      - '5432'
```

Spring Boot connects using:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
```

## 8. API Documentation (Swagger)

* UI: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
* OpenAPI Spec: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

## 9. Future Enhancements

* Kafka for async events like sale created, product inventory updates
* Authorization & Authentication (JWT or OAuth)
* Rate limiting, filtering

# 🛒 WSD Senior Java Developer Challenge

A Spring Boot 3.2.2-based E-commerce backend system built using **Test-Driven Development (TDD)** principles. This API provides customer wishlist and sales analytics endpoints with production-ready caching, containerized infrastructure, and developer tooling.

---

## ✅ Features

- 📦 Retrieve **customer wishlists**
- 💰 Get **total sale amount of today**
- 📊 Get **day with highest sales** in a date range
- 🏆 Get **top 5 selling items (all-time)** by revenue
- 📈 Get **top 5 selling items (last month)** by quantity
- 🧪 Built using **TDD** (JUnit 5, Mockito, Testcontainers)
- 🗃️ **PostgreSQL** in Docker for persistence
- ⚡ **Redis caching** with eviction
- 🔍 **Logstash + JSON logs** for analysis-ready logs
- 📓 Interactive API docs with **Swagger**

---

## 🧰 Tech Stack

| Layer        | Tool/Framework                |
|--------------|-------------------------------|
| Language     | Java 21                       |
| Framework    | Spring Boot 3.2.2             |
| Database     | PostgreSQL (Dockerized)       |
| Testing      | JUnit 5, Mockito, Testcontainers |
| Caching      | Redis with Spring Cache       |
| Logging      | Logstash Logback Encoder      |
| API Docs     | Springdoc OpenAPI (Swagger UI)|
| Dev Tools    | Docker, Gradle (Groovy DSL)   |

---

## 🚀 Getting Started

### 1. Prerequisites

- Java 21+
- Docker & Docker Compose
- Gradle (`./gradlew` is included)

---

### 2. Clone and Run

```bash
git clone https://github.com/syridit/wsd-sjd-challenge.git
cd wsd-sjd-challenge

# Start containers
docker compose up -d

# Run the application
./gradlew bootRun
````

---

### 3. API Documentation

Visit: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧪 Running Tests

```bash
./gradlew test
```

> 🔬 Uses **Testcontainers** for isolated PostgreSQL during tests.

---

## 💾 Data Generation

Initial data is inserted using a Backdoor API. Check swagger.

---

## 📁 Environment Configuration

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce_db
spring.datasource.username=ecommerce_user
spring.datasource.password=ecommerce_pass

spring.redis.host=localhost
spring.redis.port=6379
spring.cache.type=redis
```
---

## 📈 Logging

Logs are JSON-formatted via **Logstash Logback Encoder**, ready for centralized log analysis systems like ELK.

---

## NOTE
In compose.yml, ELK containers are commented out due to some issues in my own pc memory issue. If you want to look into the ELK tools, you can uncomment the lines and run again with ELK support. Logs can be monitored by visiting http://localhost:5601 and creating relevant index `spring-boot-*`

## 🧑‍💻 Author

**Md. Sadman Yasar Ridit**
Email: `syridit.prof@gmail.com`
GitHub: [syridit](https://github.com/syridit)

---


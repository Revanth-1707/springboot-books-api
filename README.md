
# 📚 Books & Authors REST API

A simple Spring Boot REST API for managing books and authors using PostgreSQL and Spring Data JPA.

---

## 🚀 Features

- CRUD operations for books and authors
- RESTful endpoints
- PostgreSQL as backend database
- Integration tests using MockMvc and H2 (in-memory)
- Docker support for PostgreSQL (optional)

---

## 🧰 Tech Stack

- Java 17+
- Spring Boot 3.x
- Spring Web & Spring Data JPA
- PostgreSQL / H2 (for testing)
- Maven
- JUnit 5 & MockMvc
- Docker (optional)

---

## 📁 Project Structure

```

.
├── src/
│   ├── main/
│   │   ├── java/com/revtech/...
│   │   │   ├── config/
│   │   │   ├── domain/entites/
│   │   │   ├── domain/dto/
│   │   │   ├── controllers/
│   │   │   ├── mappers/
│   │   │   ├── mappers/impl
│   │   │   ├── models/
│   │   │   ├── repositories/
│   │   │   ├── services/
│   │   │   └── services/impl
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/revtech/...
├── docker-compose.yml (optional)
├── pom.xml
└── README.md

````

---

## 🐳 Using Docker (Optional)

To run PostgreSQL with Docker:

```bash
docker-compose up -d
````

To stop:

```bash
docker-compose down
```

---
Great! Here's a section you can **add to your `README.md`** file to instruct users how to create the `application.properties` file securely.

---

## ⚙️ Configuration

Create a file named application.properties inside src/main/resources/ directory:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bookdb
spring.datasource.username=root
spring.datasource.password=yourpassword

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> Use `update` for development; change to `none` or `validate` in production.

**⚠️ Do not commit this file to the repository.**
Make sure to add the following line to your `.gitignore`:

```bash
src/main/resources/application.properties
```

---

## 📡 API Endpoints

### 📘 Books

| Method | Endpoint          | Description    |
| ------ | ----------------- | -------------- |
| GET    | `/api/books`      | List all books |
| GET    | `/api/books/{id}` | Get book by ID |
| POST   | `/api/books`      | Add new book   |
| PUT    | `/api/books/{id}` | Update book    |
| DELETE | `/api/books/{id}` | Delete book    |

### 🧑 Authors

| Method | Endpoint            | Description      |
| ------ | ------------------- | ---------------- |
| GET    | `/api/authors`      | List all authors |
| GET    | `/api/authors/{id}` | Get author by ID |
| POST   | `/api/authors`      | Add new author   |
| PUT    | `/api/authors/{id}` | Update author    |
| DELETE | `/api/authors/{id}` | Delete author    |

---

## 🧪 Run Tests

```bash
mvn clean test
```

---

## 👨‍💻 Author

**Revanth L**
Java Developer | Java | Spring Boot

# Record Manager

## Table of Contents

- [About](#-about)
- [How to Use](#-how-to-use)
- [Main Task](#-main-task)
- [Additional Task](#-additional-task)

## 🚀 About

This project is a backend application designed to track users' expenses and income. It includes features like:
- Expense and income management with support for custom categories.
- A PostgreSQL database for persistent storage.
- Full validation, error handling, and integration with an ORM (Spring Data JPA).

The application is built with **Java 17**, **Spring Boot**, **JPA**, and **PostgreSQL**. It follows modern backend development practices, including clean architecture, DTOs, and migrations for database schema management.

## 📝 How to Use

1. **Prerequisites**:
    - Java 17 installed.
    - Docker and Docker Compose installed for database setup.
    - Postman or similar tool for API testing.

2. **Setup**:
    - Clone the repository:
      ```bash
      git clone https://github.com/kol-oss/record-backend.git
      ```
    - Build the application:
      ```bash
      ./gradlew build
      ```
    - Get `.env` file or set up environment variables:
      ```bash
      export DATABASE_HOST = <db_address>
      export POSTGRES_DB_NAME = <db_name>
      export POSTGRES_USERNAME = <db_username>
      export POSTGRES_PASSWORD = <db_password>
      ```
    - Set up the database using Docker Compose:
      ```bash
      docker-compose up -d
      ```

3. **Environment Variables**:
   Configure database credentials and other settings in the `application.yaml` file or as environment variables:
   ```properties
   spring.datasource.url=jdbc:postgresql://<db_address>:5432/<db_name>
   spring.datasource.username=<db_username>
   spring.datasource.password=<db_password>
   ```

4. **Testing**:
    - Use the provided [Postman flow](https://www.postman.com/evanphilips/workspace/my-workspace/flow/674b6ff20f2a8a76568699fa) to test endpoints.
    - New data structures are included to the output of the old resources' endpoints.

## 📚 Main Task

### Validation and Error Handling

- Controller's input validation is implemented using **Jakarta Validation** annotations on DTOs like `@NotNull`, `@NotEmpty` or `@Size` and Spring Web **ResponseStatusException** to inform user about issues from Service level.

- Entities' validation is performed by **Hibernate Validation** annotations like `@Column(nullable = false, length = 64)`.

### Database and migrations

- Data access layer is implemented in [Repositories](https://github.com/kol-oss/record-backend/tree/main/src/main/java/edu/kpi/backend/repository) level via Spring Boot and JPA frameworks.

- Connection properties are defined in [application.yaml](https://github.com/kol-oss/record-backend/blob/main/src/main/resources/application.yaml) file and configured to use [migrations](https://github.com/kol-oss/record-backend/tree/main/src/main/resources/db/migration), use **PostgresSQL** database and only update existing tables without their creation or deletion by the end of the application. 

- Database tables include: `users`, `categories`, `records` and `accounts`. Pay attention that cascade deletion is allowed and deletion of one entity can cause deletion of others.

### Docker and docker-compose

- [Dockerfile](https://github.com/kol-oss/record-backend/blob/main/Dockerfile) contains application initialization **without** configuring database server.

- Both application and database containers are configured in [docker-compose.yaml](https://github.com/kol-oss/record-backend/blob/main/docker-compose.yaml) file with next options: Spring server will be run on your machine's port **8080** and database on port **5432**. Database host, name, username and password **are hidden** will be taken from environment variables.

- To get environment variables, contact with developer and ask for `.env` file or add them to your hosting environment.

## 🔧 Additional Task

### Variant: 3 (Incomes account management)

This implementation introduces an **Income Tracking** feature:
1. **Entity: `Account`**:
    - Each user has an account entity for income tracking.
    - Income is credited to the account, and expenses are debited from it automatically.
    - Negative balances are **allowed** by default.
    - Account entity can not be accessed from endpoints directly due to the security issues.
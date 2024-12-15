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
- Basic JWT authorization with corresponding endpoints.

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
      export JWT_SECRET = <jwt_secret>
      export DATABASE_URL = <db_url>
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
   spring.datasource.url=jdbc:<db_url>
   spring.datasource.username=<db_username>
   spring.datasource.password=<db_password>
   ```

4. **Testing**:
    - Use the provided [Postman flow](https://www.postman.com/evanphilips/workspace/my-workspace/flow/675ef40aa8da07003122e688) to test endpoints.
    - Try to interact with [deploy](https://healthcheck-8g8l.onrender.com) of the application via Render.com hosting.
    - New data structures are included to the output of the old resources' endpoints.

## 📚 Main Task

### Validation and Error Handling

- Controller's input validation is implemented using **Jakarta Validation** annotations on DTOs like `@NotNull`, `@NotEmpty` or `@Size` and Spring Web **ResponseStatusException** to inform user about issues from Service level.

- Entities' validation is performed by **Hibernate Validation** annotations like `@Column(nullable = false, length = 64)`.

- Authentication errors on permitted endpoints without logging are represented in null responses with **403 (Forbidden)** response status according to framework's mechanisms. If user try login with invalid name or password, full error response will be returned (see example in Postman flow). 

### Database and migrations

- Data access layer is implemented in [Repositories](https://github.com/kol-oss/record-backend/tree/main/src/main/java/edu/kpi/backend/repository) level via Spring Boot and JPA frameworks.

- Connection properties are defined in [application.yaml](https://github.com/kol-oss/record-backend/blob/main/src/main/resources/application.yaml) file and configured to use [migrations](https://github.com/kol-oss/record-backend/tree/main/src/main/resources/db/migration), use **PostgresSQL** database and only update existing tables without their creation or deletion by the end of the application. 

- Database tables include: `users`, `categories`, `records` and `accounts`. Pay attention that cascade deletion is allowed and deletion of one entity can cause deletion of others.

### Docker and docker-compose

- [Dockerfile](https://github.com/kol-oss/record-backend/blob/main/Dockerfile) contains application initialization **without** configuring database server.

- Both application and database containers are configured in [docker-compose.yaml](https://github.com/kol-oss/record-backend/blob/main/docker-compose.yaml) file with next options: Spring server will be run on your machine's port **8080** and database on port **5432**. Database host, name, username and password **are hidden** will be taken from environment variables.

- To get environment variables, contact with developer and ask for `.env` file or add them to your hosting environment.

### JWT authentication

- Authentication and authorization are performed via **Spring Security** framework and **JJWT** library. Token generation logic is placed in [JWT Utils](https://github.com/kol-oss/record-backend/blob/main/src/main/java/edu/kpi/backend/utils/JwtTokenUtils.java) class. The token's living time is defined in project configuration's file.

- Security configuration is performed via [Request filter](https://github.com/kol-oss/record-backend/blob/main/src/main/java/edu/kpi/backend/config/JwtRequestFilter.java) abstraction, which uses defined settings from [Security Config](https://github.com/kol-oss/record-backend/blob/main/src/main/java/edu/kpi/backend/config/SecurityConfig.java) class. List of endpoint which requires authorization is also defined there:

```shell
    .requestMatchers("/auth/**").permitAll()
    .requestMatchers("/users/**").authenticated()
    .requestMatchers("/categories/**").authenticated()
    .requestMatchers("/records/**").authenticated()
    .requestMatchers("/accounts/**").authenticated()
```

- User endpoint's controller is now replaced with [Auth controller](https://github.com/kol-oss/record-backend/blob/main/src/main/java/edu/kpi/backend/controller/AuthController.java) with methods for registering and logging users. Pay attention, that user can communicate with other endpoints only after sending `/auth/login` request and getting **Bearer token** in response. This token must be used in other requests to perform JWT.

## 🔧 Additional Task

### Variant: 3 (Incomes account management)

This implementation introduces an **Income Tracking** feature:
1. **Entity: `Account`**:
    - Each user has an account entity for income tracking.
    - Income is credited to the account, and expenses are debited from it automatically.
    - Negative balances are **allowed** by default.
    - Account entity can not be updated from endpoints directly due to the security issues.
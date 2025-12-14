# Spring Boot Security JWT

A Spring Boot application demonstrating JWT (JSON Web Token) based authentication and authorization.

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Setup Instructions](#setup-instructions)
- [Database Configuration](#database-configuration)
- [API Endpoints](#api-endpoints)
- [Security Configuration](#security-configuration)
- [JWT Implementation](#jwt-implementation)
- [Running the Application](#running-the-application)
- [Testing the APIs](#testing-the-apis)
- [Swagger Documentation](#swagger-documentation)
- [Contributing](#contributing)
- [License](#license)

## Overview

This project demonstrates a complete implementation of JWT-based authentication and authorization in a Spring Boot application. It includes user registration, login, JWT token generation, and role-based access control.

## Features

- User Registration and Login
- JWT Token Generation and Validation
- Role-Based Access Control (RBAC)
- Password Encryption using BCrypt
- RESTful API Design
- Swagger API Documentation
- Exception Handling
- Input Validation
- CORS Configuration

## Technologies Used

- Java 17
- Spring Boot 4.0.0
- Spring Security
- Spring Data JPA
- MySQL Database
- JWT (JSON Web Tokens) - jjwt 0.11.5
- Maven
- Lombok
- SpringDoc OpenAPI (Swagger UI)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/springsecurity/spring_boot_security_jwt/
│   │       ├── config/              # Security and Swagger configuration
│   │       ├── controller/          # REST controllers
│   │       ├── dto/                 # Data Transfer Objects
│   │       │   ├── request/         # Request DTOs
│   │       │   └── response/        # Response DTOs
│   │       ├── exception/           # Custom exceptions and handlers
│   │       ├── jwt/                 # JWT utilities and filters
│   │       ├── model/               # JPA entities
│   │       ├── repository/          # JPA repositories
│   │       ├── service/             # Business logic services
│   │       │   └── impl/            # Service implementations
│   │       ├── util/                # Utility classes
│   │       └── SpringBootSecurityJwtApplication.java # Main application class
│   └── resources/
│       └── application.properties    # Application configuration
└── test/                            # Test classes
```

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- IDE (IntelliJ IDEA, Eclipse, etc.)

## Setup Instructions

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/spring-boot-security-jwt.git
   ```

2. Navigate to the project directory:
   ```bash
   cd spring-boot-security-jwt
   ```

3. Update the database configuration in `src/main/resources/application.properties`:
   ```
   spring.datasource.url=jdbc:mysql://localhost:3306/jwt_db
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. Create the database in MySQL:
   ```sql
   CREATE DATABASE jwt_db;
   ```

5. Build the project:
   ```bash
   mvn clean install
   ```

## Database Configuration

The application uses MySQL as the database. Update the following properties in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/jwt_db
spring.datasource.username=root
spring.datasource.password=root1
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
```

## API Endpoints

| Method | Endpoint           | Description              | Access      |
|--------|--------------------|--------------------------|-------------|
| POST   | `/auth/signUp`     | Register a new user      | Public      |
| POST   | `/auth/signIn`     | Authenticate user        | Public      |
| POST   | `/auth/getUserDetails` | Get user details     | Admin only  |
| GET    | `/auth/`           | Check if app is running  | Public      |

## Security Configuration

The security configuration is defined in [SecurityConfig.java](src/main/java/com/springsecurity/spring_boot_security_jwt/config/SecurityConfig.java):

- CSRF protection disabled
- Stateless session management
- Role-based access control
- JWT authentication filter
- CORS configuration
- Password encryption with BCrypt

Public endpoints:
- `/auth/**` (Authentication APIs)
- Swagger/OpenAPI endpoints

## JWT Implementation

JWT functionality is implemented in the [jwt](src/main/java/com/springsecurity/spring_boot_security_jwt/jwt/) package:

- [JwtTokenHelper.java](src/main/java/com/springsecurity/spring_boot_security_jwt/jwt/JwtTokenHelper.java): Handles JWT token generation and validation
- [JwtAuthenticationFilter.java](src/main/java/com/springsecurity/spring_boot_security_jwt/jwt/JwtAuthenticationFilter.java): Intercepts requests to validate JWT tokens
- [CustomUserDetailService.java](src/main/java/com/springsecurity/spring_boot_security_jwt/jwt/CustomUserDetailService.java): Loads user details for authentication
- [JwtAuthenticationEntryPoint.java](src/main/java/com/springsecurity/spring_boot_security_jwt/jwt/JwtAuthenticationEntryPoint.java): Handles unauthorized access

JWT Configuration in `application.properties`:
```properties
jwt.secret.key=YWJjZGVmZ2hpamtsbW5vcHFyc3R1dnd4eXphYmNkZWZnaGlqa2xtbm9wcXJzdHV2d3h5emFiY2RlZmdoaWprbG1ub3BxcnN0dXZ3eHl6
jwt.token.expire.validity=86400000
```

## Running the Application

Run the application using Maven:
```bash
mvn spring-boot:run
```

Or run the main class [SpringBootSecurityJwtApplication.java](src/main/java/com/springsecurity/spring_boot_security_jwt/SpringBootSecurityJwtApplication.java) from your IDE.

The application will start on port 8081 with context path `/api`.

## Testing the APIs

### 1. User Registration
```bash
curl -X POST http://localhost:8081/api/auth/signUp \
  -H "Content-Type: application/json" \
  -d '{
    "name": "John Doe",
    "email": "john@example.com",
    "password": "password123"
  }'
```

### 2. User Login
```bash
curl -X POST http://localhost:8081/api/auth/signIn \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john@example.com",
    "password": "password123"
  }'
```

Save the JWT token from the response for subsequent requests.

### 3. Access Protected Endpoint
```bash
curl -X POST http://localhost:8081/api/auth/getUserDetails \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "email": "john@example.com"
  }'
```

## Swagger Documentation

API documentation is available through Swagger UI:
- URL: http://localhost:8081/api/swagger-ui/index.html
- API Docs: http://localhost:8081/api/v3/api-docs

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
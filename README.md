# **Email Authentication Service API**

This microservice is responsible for managing user authentication, including registration, login, and JWT token generation. It is part of a larger microservices architecture and ensures secure access to various system functionalities.

## **Features**

- **User Registration**: Create a new user with first name, last name, email, and password.
- **User Login**: Authenticate a user with email and password, returning a JWT access token.
- **JWT Token Management**: Generate and validate JWT tokens for secure communication.

## **Architecture**

This service uses the following technologies and principles:

- **Spring Boot**: For building the RESTful API.
- **Spring Security**: To handle authentication and authorization.
- **Hibernate & JPA**: For data persistence in a PostgreSQL database.
- **Jakarta Validation**: For validating incoming DTOs.
- **JWT**: For generating secure tokens.
- **Swagger/OpenAPI**: To document API endpoints.

## **Folder Structure**

```graphql
graphql
Copy code
email-authentication-service-api/
│
├── common/
│   ├── constant/              # API route constants.
│   ├── dto/                   # Request and response data transfer objects (DTOs).
│   └── entity/                # JPA entity classes (UserModel).
│
├── config/                    # Spring Security and JWT configuration files.
├── controller/                # API interfaces and their implementations.
├── repository/                # JPA repositories for database operations.
├── service/                   # Service layer interfaces and their implementations.
├── resources/
│   └── application.yaml       # Configuration file (DB, security, etc.).
└── tests/                     # Unit and integration tests.

```

## **Endpoints**

### **Base Path**

**`/v1/auth`**

### **User Registration**

**POST** **`/register`**

- **Description**: Register a new user.
- **Request Body**:

    ```json
    json
    Copy code
    {
      "firstName": "John",
      "lastName": "Doe",
      "email": "john.doe@example.com",
      "password": "securePassword123"
    }
    
    ```

- **Response**:

    ```json
    json
    Copy code
    {
      "accessToken": "jwt-token-string"
    }
    
    ```


### **User Login**

**POST** **`/login`**

- **Description**: Authenticate a user and provide an access token.
- **Request Body**:

    ```json
    json
    Copy code
    {
      "email": "john.doe@example.com",
      "password": "securePassword123"
    }
    
    ```

- **Response**:

    ```json
    json
    Copy code
    {
      "accessToken": "jwt-token-string"
    }
    
    ```


## **Security**

The application uses **JWT** tokens to handle user authentication. The tokens are validated through a custom **`JwtAuthFilter`**. Sensitive user data is encrypted using **BCryptPasswordEncoder**.

## **Database Schema**

### **Users Table**

| **Column** | **Type** | **Constraints** |
| --- | --- | --- |
| userId | BIGINT | Primary Key, Auto-Increment |
| email | VARCHAR | Unique, Not Null |
| password | VARCHAR | Not Null |
| firstName | VARCHAR | Not Null |
| lastName | VARCHAR | Not Null |
| role | VARCHAR | Not Null |

## **Configuration**

### **application.yaml**

```yaml
yaml
Copy code
spring:
  datasource:
    url: jdbc:postgresql://<DB_HOST>:<DB_PORT>/<DB_NAME>
    username: <DB_USER>
    password: <DB_PASSWORD>

jwt:
  secret: your-secret-key

```

## **Running the Application**

1. Clone the repository.
2. Configure the **`application.yaml`** file with your database and JWT secret.
3. Build and run the project:

    ```bash
    bash
    Copy code
    ./mvnw spring-boot:run
    
    ```


## **Swagger Documentation**

Access the API documentation at:

**`http://localhost:8080/swagger-ui.html`**

## **Future Enhancements**

- Add password recovery functionality.
- Implement user role management.
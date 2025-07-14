# IDS - Software Engineer Team Project

A comprehensive Spring Boot application for managing local supply chains.

## 🤝 Team
- Francesco Calicchio
- Younes Rabeh
- Alberto Nobilioni

## 🚀 Features

- **Product Management**: Create, update, and manage products with categorization
- **Bundle System**: Group products into bundles with custom pricing
- **Certification Process**: Approval workflow for products and bundles
- **User Authentication**: JWT-based authentication with role-based access control
- **Company Management**: Multi-tenant support for different companies
- **Search & Filtering**: Advanced search capabilities for products and bundles
- **File Storage**: Secure file upload and management system
- **Purchase System**: Handle product and bundle purchases with status tracking

## 🛠️ Technology Stack

- **Framework**: Spring Boot 3.5.0
- **Language**: Java 21
- **Database**: PostgreSQL
- **Security**: Spring Security with JWT
- **ORM**: Spring Data JPA with Hibernate
- **Validation**: Bean Validation
- **Build Tool**: Gradle
- **Mapping**: MapStruct
- **Architecture**: Clean Architecture with Domain-Driven Design

## 📋 Prerequisites

- Java 21 or higher
- PostgreSQL database
- Gradle 7.x or higher

## 🔧 Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd ids
   ```

2. **Build the project**
   ```bash
   ./gradlew build
   ```

3. **Run the application**
   ```bash
   ./gradlew bootRun
   ```

The application will start on `http://localhost:8080/api`

## 🔍 Usage

You can access the API using tools like Postman or cURL. Make sure to include the JWT token in the `Authorization` header for protected endpoints.

For the json Postman collection, you can find it [here](./ids.postman_collection.json)

## 🏗️ Project Structure

```
src/main/java/it/unicam/cs/ids/
├── IdsApplication.java              # Main application class
├── context/
│   ├── catalog/                     # Product catalog domain
│   │   ├── application/             # Application services & mappers
│   │   ├── domain/                  # Domain models & repositories
│   │   └── infrastructure/          # Controllers & persistence
│   ├── certification/               # Certification & approval domain
│   ├── company/                     # Company management domain
│   ├── identity/                    # User authentication & authorization
│   └── storage/                     # File storage domain
└── shared/                          # Shared utilities & base classes
```

## 👥 Roles

- **ADMIN**: Full system access
- **COMPANY(PRODUCER, DISTRIBUTOR, TRANSFORMER)**: Company-specific operations
- **CERTIFIER**: Has the rights to approve products and bundles
- **BUYER**: Basic user operations

## 📝 Development

### Code Style

- Use MapStruct for object mapping
- Follow Spring Boot best practices
- Implement proper exception handling
- Use DTOs for API contracts

## 📄 License

This project is part of the IDS course at University of Camerino.
# E-commerce Microservices Project

## Overview
Modern e-commerce system built with microservices architecture using Spring Cloud. This project demonstrates the implementation of scalable, resilient, and loosely coupled services.

## Architecture

```mermaid
graph TD
    Client([Client/Browser])
    
    subgraph Cloud[E-commerce Microservices]
        Gateway[API Gateway:8080]
        Registry[Service RegistryEureka :8761]
        Config[Config Server:8888]
        
        subgraph Services[Business Services]
            Products[Product Service:8081]
            Orders[Order Service:8082]
            Payments[Payment Service:8083]
            Notifications[Notification Service:8084]
        end
        
        subgraph Databases[Databases]
            ProductDB[(Product DB)]
            OrderDB[(Order DB)]
            PaymentDB[(Payment DB)]
        end
    end

    Client -->|HTTP/HTTPS| Gateway
    Gateway -->|Load Balanced| Products
    Gateway -->|Load Balanced| Orders
    Gateway -->|Load Balanced| Payments
    Gateway -->|Load Balanced| Notifications
    
    Products --> ProductDB
    Orders --> OrderDB
    Payments --> PaymentDB
    
    Products -.->|Register| Registry
    Orders -.->|Register| Registry
    Payments -.->|Register| Registry
    Notifications -.->|Register| Registry
    Gateway -.->|Register| Registry
    
    Products -->|Get Config| Config
    Orders -->|Get Config| Config
    Payments -->|Get Config| Config
    Notifications -->|Get Config| Config
    Gateway -->|Get Config| Config

    Orders -->|Feign Client| Products
    Payments -->|Feign Client| Orders
    Notifications -->|Listen Events| Orders
    
    style Cloud fill:#f5f5f5,stroke:#333,stroke-width:2px
    style Databases fill:#fff,stroke:#333,stroke-width:2px
    style Services fill:#fff,stroke:#333,stroke-width:2px
    
    classDef service fill:#e1f5fe,stroke:#01579b,stroke-width:2px;
    classDef database fill:#e8f5e9,stroke:#2e7d32,stroke-width:2px;
    classDef infrastructure fill:#fce4ec,stroke:#880e4f,stroke-width:2px;
    
    class Products,Orders,Payments,Notifications service;
    class ProductDB,OrderDB,PaymentDB database;
    class Gateway,Registry,Config infrastructure;
```

### Service Interaction
- **Client** makes requests through the **API Gateway**
- **Gateway** routes requests to appropriate services
- All services register with **Eureka** for service discovery
- Services fetch configuration from **Config Server**
- **Order Service** communicates with **Product Service** using Feign Client
- **Payment Service** validates orders through **Order Service**
- **Notification Service** listens to events from **Order Service**

### Services
- **Service Registry (Eureka)**: Service discovery and registration
- **Config Server**: Centralized configuration management
- **API Gateway**: Single entry point for all client requests
- **Product Service**: Product catalog management
- **Order Service**: Order processing and management
- **Payment Service** *(planned)*: Payment processing
- **Notification Service** *(planned)*: Email and notification handling

## Technologies
- Java 17
- Spring Boot 3.x
- Spring Cloud
- PostgreSQL
- Docker *(planned)*
- Kubernetes *(planned)*

## Prerequisites
- JDK 17
- Maven
- PostgreSQL 16.7
- Git

## Getting Started

### Database Setup
```sql
-- Create databases
CREATE DATABASE ecommerce_product;
CREATE DATABASE ecommerce_order;
```

### Service Configuration
Each service runs on a different port:
- Eureka Server: 8761
- Config Server: 8888
- API Gateway: 8080
- Product Service: 8081
- Order Service: 8082

### Building and Running

1. Clone the repository
```bash
git clone https://github.com/leeok4/ecommerce-microservices.git
cd ecommerce-microservices
```

2. Start services in order:
```bash
# 1. Service Registry
cd service-registry
mvn spring-boot:run

# 2. Config Server
cd ../config-server
mvn spring-boot:run

# 3. API Gateway
cd ../api-gateway
mvn spring-boot:run

# 4. Product Service
cd ../product-service
mvn spring-boot:run
```

## API Documentation

### Product Service
- Create Product: `POST /api/products`
```bash
curl -X POST http://localhost:8080/api/products \
-H "Content-Type: application/json" \
-d "{\"name\":\"Smartphone XYZ\",\"description\":\"Latest model smartphone\",\"price\":999.99,\"category\":\"Electronics\",\"stockQuantity\":50}"
```

- Get All Products: `GET /api/products`
```bash
curl -X GET http://localhost:8080/api/products
```

- Get Product by ID: `GET /api/products/{id}`
```bash
curl -X GET http://localhost:8080/api/products/1
```

- Update Product: `PUT /api/products/{id}`
```bash
curl -X PUT http://localhost:8080/api/products/1 \
-H "Content-Type: application/json" \
-d "{\"name\":\"Smartphone XYZ Pro\",\"description\":\"Latest model smartphone - Pro version\",\"price\":1299.99,\"category\":\"Electronics\",\"stockQuantity\":30}"
```

- Delete Product: `DELETE /api/products/{id}`
```bash
curl -X DELETE http://localhost:8080/api/products/1
```

## Project Structure
```
ecommerce-microservices/
├── service-registry/       # Eureka Server
├── config-server/         # Configuration Server
├── api-gateway/          # API Gateway
├── product-service/     # Product Management
└── order-service/      # Order Management (In Progress)
```

## Future Enhancements
- [ ] Implementation of Payment Service
- [ ] Implementation of Notification Service
- [ ] Docker containerization
- [ ] Kubernetes deployment
- [ ] Distributed tracing
- [ ] Circuit breaker implementation
- [ ] API documentation with Swagger
- [ ] Improved error handling
- [ ] Service monitoring
- [ ] Integration tests

## Contributing
Feel free to submit issues and enhancement requests.

## License
This project is licensed under the MIT License - see the LICENSE file for details
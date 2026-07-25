

https://github.com/user-attachments/assets/abad6eed-5f9d-4a9d-b797-bb9209ee3cf2


<img width="5100" height="808" alt="mermaid-diagram" src="https://github.com/user-attachments/assets/77771f91-658a-4854-ae87-6ecf8a76fb74" />

## Project Workflow

### 1. Flight Data Collection

The application continuously collects real-time flight data from around the world.

The incoming flight data is published to **Apache Kafka**, which acts as the central streaming platform for handling high-volume flight data.

### 2. Kafka-Based Data Processing

Kafka consumers receive the incoming flight data and process it in parallel.

This architecture allows the application to efficiently handle a large number of flight records and distribute the processing workload across multiple consumers.

### 3. Flight Filtering

Flight filters are applied based on user preferences and the current map area.

Users can filter flight data based on criteria such as:

* Aircraft type
* Altitude
* Speed

The visible map coordinates are also sent from the React client through WebSocket. This allows the backend to process and return flight data relevant to the user's current map area.

### 4. Real-Time WebSocket Communication

The React frontend maintains a WebSocket connection with the Spring Boot backend.

The client sends the current map coordinates and selected flight filters through WebSocket. After processing the flight data, the backend sends the filtered real-time flight information back to the client without requiring continuous HTTP polling.

This provides faster and more efficient real-time communication.

### 5. Interactive Flight Map

The React application uses **React Leaflet** to display flight data on an interactive map.

As users move or zoom the map, the current map coordinates are sent to the backend. The backend then filters the available flight data based on the selected area and user filters.

### 6. Premium User Features

Premium users have access to additional flight filtering options.

They can filter flights based on advanced criteria such as aircraft or helicopter type, altitude, and speed.

This provides premium users with more detailed control over the flight data displayed on the map.

### 7. Authentication & Authorization

User authentication is handled securely using cookie-based authentication.

The application uses **Spring Security** for authentication and authorization. Protected functionality is secured using role-based access control and method-level security.

User and role information is stored in the database and used to control access to protected features.

### 8. Database

The database is used to store application-related user information rather than real-time flight data.

It stores information such as:

* Users
* Roles
* Authentication and authorization data

Real-time flight data is processed through the Kafka pipeline and is not stored in the database.

### 9. Docker Deployment

The application and its required services are containerized using **Docker**.

The complete application environment can be started using **Docker Compose**, allowing the backend, frontend, Kafka, database, and other required services to run together in a consistent environment.


To stop the application:

docker compose down
Technologies Used
Frontend: React, Vite, TypeScript
Backend: Spring Boot, POM
Database: PostgreSQL
Caching: Redis
Message Broker: RabbitMQ
Real-Time Communication: WebSocket (STOMP)
Dynamic Queries: QueryDSL
Authentication: JWT with HTTP-Only Cookies
Authorization: Role-Based Access Control (RBAC)
Password Security: BCrypt

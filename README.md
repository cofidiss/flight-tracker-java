

https://github.com/user-attachments/assets/abad6eed-5f9d-4a9d-b797-bb9209ee3cf2


<img width="5100" height="808" alt="mermaid-diagram" src="https://github.com/user-attachments/assets/77771f91-658a-4854-ae87-6ecf8a76fb74" />

Real-Time Flight Tracking System

A real-time flight tracking application that collects global flight data, processes it with Apache Kafka, filters flights based on user preferences, and delivers real-time updates to the frontend via WebSocket.

Features
🌍 Global real-time flight tracking
🚀 Apache Kafka for flight data streaming
⚡ Parallel flight data processing and filtering
🔄 Real-time updates with WebSocket
📍 Dynamic map-based coordinate filtering
✈️ Premium filters for aircraft/helicopter type, altitude, and speed
🔐 Cookie-based authentication
🛡️ Authorization with Spring Security
🗺️ Interactive maps with React Leaflet
🐳 Docker Compose deployment
🗄️ Database integration
Architecture
Flight Data
    ↓
Apache Kafka
    ↓
Parallel Processing & Filtering
    ↓
WebSocket
    ↓
React + React Leaflet
    ↓
Real-Time Flight Map

When users change the visible map area, the relevant coordinates are sent via WebSocket. The backend filters the flight data accordingly and sends only relevant flights to the frontend.

The application uses Java, Spring Boot, Gradle, Apache Kafka, WebSocket, React, React Leaflet, Docker, Docker Compose, and a database.

Running with Docker
docker compose up -d

To stop the application:

docker compose down

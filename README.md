# URL Shortener Service

A high-performance URL shortener built with Spring Boot 3.2, PostgreSQL, and Redis. This project demonstrates modern backend practices including caching, database transactions, and Docker containerization.

## 🚀 Features

- **REST API**: Exposes standard RESTful endpoints for URL management.
- **URL Shortening**: Converts long URLs into short, unique 7-character codes (Base62 encoding).
- **High Performance**: Uses Redis for caching to ensure sub-millisecond redirect times.
- **Persistence**: Stores mappings reliably in PostgreSQL.
- **Fault Tolerance**: Gracefully handles Redis failures by falling back to the database.
- **Dockerized**: Full infrastructure (DB + Cache) runs in Docker containers.

---

## 🛠️ Tech Stack

- **Language**: Java 17+ (Compatible with Java 21/25)
- **Framework**: Spring Boot 3.2
- **Architecture**: REST API
- **Database**: PostgreSQL 15
- **Cache**: Redis 7
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

---

## ⚙️ Prerequisites

Before you begin, ensure you have the following installed:
- **Java Development Kit (JDK) 17** or higher
- **Docker Desktop** (for running the database and cache)
- **Git**

---

## 🏁 Getting Started (Zero to Hero)

Follow these steps to set up the development environment from scratch.

### 1. Clone the Repository
```bash
git clone https://github.com/kamalmurmu1/shorturl-app.git
cd url-shortener
```

### 2. Start Infrastructure
We use Docker to run PostgreSQL and Redis so you don't have to install them manually.

**Windows:**
Double-click `start-infra.bat` or run:
```cmd
.\start-infra.bat
```

**Linux/Mac:**
```bash
docker compose up -d
```

*Verify containers are running:*
```bash
docker compose ps
```
You should see `postgres` and `redis` with status `Up`.

### 3. Build the Project
Download dependencies and compile the code.
```bash
./mvnw clean install
```

### 4. Run the Application
You can run the app directly from the terminal or your IDE.

**Terminal:**
```bash
./mvnw spring-boot:run
```

**IDE (IntelliJ / Eclipse / VS Code):**
- Open the project as a Maven project.
- Locate `src/main/java/com/example/shortener/ShortenerApplication.java`.
- Right-click and select **Run 'ShortenerApplication'**.

The application will start on `http://localhost:8080`.

---

## 🔌 API Documentation

The application provides a REST API to interact with the service. You can use tools like **Postman**, **cURL**, or your browser to test these endpoints.

### 1. Shorten a URL
Creates a new short URL for the given long URL.

- **Endpoint**: `POST /shorten`
- **Content-Type**: `application/json`

**Request Body:**
```json
{
  "url": "https://www.google.com/search?q=spring+boot+tutorial"
}
```

**Response (200 OK):**
Returns the full shortened URL as a plain string.
```text
http://localhost:8080/a1b2c3d
```

**cURL Example:**
```bash
curl -X POST http://localhost:8080/shorten \
     -H "Content-Type: application/json" \
     -d '{"url": "https://www.example.com"}'
```

### 2. Redirect to Original URL
Redirects the user to the original long URL associated with the short code.

- **Endpoint**: `GET /{shortCode}`
- **Example**: `GET /a1b2c3d`

**Response:**
- **302 Found**: Automatically redirects the browser/client to the original URL.
- **404 Not Found**: If the short code does not exist in the database.

**Browser Test:**
Simply paste the shortened URL (e.g., `http://localhost:8080/a1b2c3d`) into your browser's address bar.

---

## 🐛 Troubleshooting

**1. "Connection refused" or Database errors:**
- Ensure Docker is running (`docker compose ps`).
- If you see timezone errors (e.g., `Asia/Calcutta`), the app is already configured to force UTC, but ensure you have the latest changes.

**2. Port 8080 already in use:**
- Stop other services running on port 8080.
- Or change `server.port` in `src/main/resources/application.properties`.

**3. Lombok errors in IDE:**
- Ensure "Enable Annotation Processing" is checked in your IDE settings.
- If using IntelliJ, install the Lombok plugin (bundled in newer versions).

---

## 📂 Project Structure

```
src/main/java/com/example/shortener
├── config          # Redis & App Configuration
├── controller      # REST API Controllers (UrlController)
├── dto             # Data Transfer Objects (UrlRequest)
├── entity          # JPA Entities (UrlMapping)
├── repository      # Data Access Layer (UrlRepository)
├── service         # Business Logic (UrlService, Base62Service)
└── ShortenerApplication.java  # Main Entry Point
```

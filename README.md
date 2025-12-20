# URL Shortener Service

## Prerequisites
- Java 17+
- Docker & Docker Compose

## How to Run

1. **Start Infrastructure** (PostgreSQL & Redis):
   ```bash
   docker-compose up -d
   ```

2. **Run the Application**:
   ```bash
   ./mvnw spring-boot:run
   ```
   Or run `ShortenerApplication.java` from your IDE.

## API Endpoints

- **Shorten URL**:
  - `POST /shorten`
  - Body: `{ "url": "https://google.com" }`
  
- **Redirect**:
  - `GET /{shortCode}`

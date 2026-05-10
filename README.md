# Claude Agentic Platform

Docker-first Spring Boot microservice designed as an experimental AI-assisted development platform using Claude Code workflows.

## 🚀 Tech Stack

- Java 21 (LTS)
- Spring Boot 3.5.x
- Maven
- Docker
- PostgreSQL (future integration)
- AWS CLI (future deployment layer)

## 📦 Current Features

- Basic Spring Boot service
- `/hello` endpoint for health validation
- Dockerized application build
- Maven-based build pipeline

## ▶️ Run Locally

### 1. Build project
```bash
mvn clean package -DskipTests
````

### 2. Run application

```bash
mvn spring-boot:run
```

### 3. Test endpoint

```
http://localhost:8080/hello
```

## 🐳 Docker

### Build image

```bash
docker build -t claude-agentic-platform .
```

### Run container

```bash
docker run -p 8080:8080 claude-agentic-platform
```

## 🧠 Concept

This project is designed as a **Claude-first development workflow**, where AI-assisted coding is part of the engineering loop, not just an auxiliary tool.

Focus areas:

* Infrastructure-first mindset
* Containerized development
* Incremental AI-driven evolution

```


Complete Rebuild:

mvn clean package -DskipTests
docker compose down
docker compose build --no-cache
docker compose up

Testable URL´s until this moment:

http://localhost:18080/hello

curl -X POST http://localhost:18080/events -H "Content-Type: application/json" -d "{\"eventType\":\"INIT\",\"message\":\"first event stored in postgres\"}"




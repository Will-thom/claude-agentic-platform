# Claude Agentic Platform

Docker-first Spring Boot microservice designed as an experimental AI-assisted development platform using Claude Code workflows.

---

## 🚀 Tech Stack

- Java 21 (LTS)
- Spring Boot 3.5.x
- Spring Data JPA
- PostgreSQL 16
- Maven
- Docker / Docker Compose
- AWS CLI (future integration)

---

## 📦 Current Status

The project is currently in a **stable foundational backend stage**, with:

- Spring Boot running inside Docker containers
- PostgreSQL fully integrated via Docker Compose
- JPA/Hibernate configured and working
- REST API endpoints functional
- Event persistence layer implemented
- Containerized development environment

---

## 🧩 Implemented Features

### API
- `GET /hello` → health validation endpoint
- `POST /events` → persists event logs in PostgreSQL

### Infrastructure
- Dockerized Spring Boot application
- Docker Compose with PostgreSQL service
- Isolated runtime environment (Docker-first execution model)

### Persistence
- JPA Entity: `EventLog`
- Spring Data Repository: `EventLogRepository`
- Automatic schema generation via Hibernate

---

## 🐳 Run Locally

### Build application
```bash id="b1k9cc"
cd app
mvn clean package -DskipTests
````

### Start full stack (app + database)

```bash id="b2m8dd"
docker compose up --build
```

### Access service

```text id="b3n7ee"
http://localhost:18080/hello
http://localhost:18080/events
```

---

## 🧠 Architecture Philosophy

This project follows a **Docker-first backend architecture**, where:

* The containerized environment is the source of truth
* Local JVM execution is not part of the main workflow
* All dependencies (DB, app, networking) are defined via Docker Compose

---

## ⚙️ Development Approach

This project is intentionally being built using a **manual, step-by-step foundational setup approach**, instead of fully automating initial scaffolding with AI tools.

### Rationale

Although AI-assisted development (e.g., Claude Code) is part of the long-term vision of this project, the initial bootstrap phase is being implemented manually for the following reasons:

* **Deep understanding of system fundamentals**
* **Full control over architecture decisions**
* **Clear visibility of each layer (Docker, Spring, JPA, DB)**
* **Better debugging and mental model formation**
* **Stronger foundation for later AI-driven evolution**

This ensures that when AI agents are introduced into the development loop, they operate on a **well-understood, intentional architecture**, rather than an opaque generated structure.

---

## 🧭 Next Steps

* Introduce Service Layer (domain separation)
* Implement event processing pipeline
* Add structured logging for “agentic events”
* Prepare architecture for Claude-driven development workflows

```


Complete Rebuild:

mvn clean package -DskipTests
docker compose down
docker compose build --no-cache
docker compose up


Testable URL´s until this moment:

http://localhost:18080/hello

curl -X POST http://localhost:18080/events -H "Content-Type: application/json" -d "{\"eventType\":\"INIT\",\"message\":\"first event stored in postgres\"}"


Resonse awaited:

{
  "id": 1,
  "eventType": "INIT",
  "message": "first event stored in postgres",
  "createdAt": "..."
}


Visualizing data inside DB docker container:


docker exec -it claude-agentic-postgres psql -U agentic -d agentic_db

\dt

 List of relations
 Schema |    Name    | Type  |  Owner
--------+------------+-------+---------
 public | event_logs | table | agentic
(1 row)


select * from event_logs;

 id |         created_at         | event_type |            message
----+----------------------------+------------+--------------------------------
  1 | 2026-05-10 19:00:28.017114 | INIT       | first event stored in postgres
(1 row)




```
## 🧱 Current Architecture Evolution (Phase 2)

The project has now evolved from a direct Controller → Repository flow into a structured service-based architecture.

### 📌 New Architecture Flow

```

HTTP Request
↓
Controller (API Layer)
↓
Service (Business Logic Layer)
↓
Repository (Data Access Layer)
↓
PostgreSQL

```

---

## 🧠 Service Layer Introduction

A dedicated service layer was introduced to:

- Isolate business logic from controllers
- Prepare the system for event-driven processing
- Enable future integration with AI/agent workflows (Claude-based processing)
- Improve testability and maintainability

### 📦 Current Service

- `EventLogService`
  - Responsible for creating and managing event logs
  - Encapsulates persistence logic
  - Acts as foundation for future event pipeline design

---

## 🚀 Design Direction

This layer is intentionally introduced as a **preparation stage for an agentic architecture**, where:

- Events will be processed beyond simple persistence
- Logs will evolve into structured “agent-readable events”
- AI hooks (Claude integration) will be introduced on top of the service layer

This ensures the system remains:
- Simple at the core
- Extensible for AI-driven workflows
- Cleanly separated by responsibility
```


Visualizing data inside DB docker container: (Phase 2)

docker compose down
docker compose up --build

curl -X POST http://localhost:18080/events -H "Content-Type: application/json" -d "{\"eventType\":\"SERVICE_TEST\",\"message\":\"testing service layer integration\"}"

{
  "id": 2,
  "eventType": "SERVICE_TEST",
  "message": "testing service layer integration",
  "createdAt": "..."
}

docker exec -it claude-agentic-postgres psql -U agentic -d agentic_db -c "select * from event_logs;"

 id |         created_at         |  event_type  |              message
----+----------------------------+--------------+-----------------------------------
  1 | 2026-05-10 19:00:28.017114 | INIT         | first event stored in postgres
  2 | 2026-05-10 19:15:20.20684  | SERVICE_TEST | testing service layer integration
(2 rows)




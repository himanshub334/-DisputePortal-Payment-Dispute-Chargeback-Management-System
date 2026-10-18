# DisputePortal — Payment Dispute & Chargeback Management System

A portfolio full-stack application for managing payment disputes and evidence uploads.

## Stack
- Java 17, Spring Boot 3, Spring Data JPA, Bean Validation
- PostgreSQL + Flyway migrations
- Redis (optional cache/service dependency in Docker Compose)
- Angular 15 + TypeScript
- Apache Tika MIME detection; pluggable antivirus scanning interface
- JUnit 5, Mockito, Swagger/OpenAPI, Docker, GitHub Actions

## Features
- Create and search disputes.
- Explicit transition-map state machine:
  `FILED → EVIDENCE_REQUESTED → UNDER_REVIEW → RESOLVED_FAVOUR_CARDHOLDER | RESOLVED_FAVOUR_MERCHANT | ESCALATED`
- Reject invalid lifecycle transitions.
- Upload evidence with extension, size and detected MIME validation.
- Evidence metadata and state transition history stored in PostgreSQL.
- REST API docs via Swagger UI.
- Angular dashboard to create disputes, list current disputes, transition lifecycle state, and upload evidence.
- Flyway schema migration and Docker Compose setup.

## Run with Docker
Requirements: Docker Desktop / Docker Engine with Compose.

```bash
docker compose up --build
```

- Angular portal: http://localhost:4200
- API: http://localhost:8080/api/disputes
- Swagger UI: http://localhost:8080/swagger-ui/index.html
- Health: http://localhost:8080/actuator/health

The demo API has no authentication enabled. Do not expose it to the public internet without adding authentication, authorization, rate limiting, and secure file storage.

## Run backend locally
Requires Java 17+ and Maven 3.9+. Start PostgreSQL (and optionally Redis), then:

```bash
cd backend
mvn spring-boot:run
```

Default local datasource is `jdbc:postgresql://localhost:5432/disputes` (user/password `disputes`). Override with `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD`.

## Run frontend locally
Requires Node.js 18+ and npm.

```bash
cd frontend
npm install
npm start
```

The Angular dev server proxies `/api` to `http://localhost:8080` using `proxy.conf.json`.

## Main endpoints
| Method | Endpoint | Purpose |
|---|---|---|
| POST | `/api/disputes` | Create dispute |
| GET | `/api/disputes` | List disputes; optional `?status=FILED` |
| GET | `/api/disputes/{id}` | Retrieve dispute |
| POST | `/api/disputes/{id}/transitions` | Change lifecycle state |
| GET | `/api/disputes/{id}/history` | Retrieve transition history |
| POST | `/api/disputes/{id}/evidence` | Upload evidence (multipart field `file`) |
| GET | `/api/disputes/{id}/evidence` | List evidence metadata |

## Security and implementation notes
- Uploaded files are validated by size, extension allowlist, and Apache Tika detected MIME type. Files are stored locally under `uploads/` in this demo. Production deployments should use private object storage, malware scanning, access-controlled downloads, retention policies, and audit controls.
- `VirusScanner` is a pluggable interface; `NoopVirusScanner` is used for local development. It does not scan for malware unless replaced with a real ClamAV integration.
- Redis is included in Compose for development, but caching is not required for core API behavior.
- This is a portfolio project and is not a certified payment platform.
- Do not claim a 0.3% upload rejection rate, 48 passed test cases, SonarQube results, or any performance metrics unless you run tests and collect those results yourself.
- Testcontainers dependency and integration-test configuration are included; verify `mvn verify` in your environment before claiming the build is green.

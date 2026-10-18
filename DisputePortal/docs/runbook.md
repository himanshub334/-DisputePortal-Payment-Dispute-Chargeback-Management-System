# Operational runbook (local demo)

## API fails to start
1. Check `docker compose ps`.
2. Inspect `docker compose logs api`.
3. Confirm PostgreSQL is healthy and `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` match the Compose service.

## Flyway migration fails
1. Inspect API logs for the failed migration version.
2. Verify database is disposable before resetting it.
3. Correct the migration in a new versioned script; don't edit an already-applied migration in shared environments.

## Evidence upload returns 400
1. Confirm extension is PDF, PNG, JPG/JPEG, TXT or CSV.
2. Ensure file is <= 10 MB.
3. Check file contents match the detected MIME type.

## Evidence upload returns 500
1. Inspect API logs and check write permissions for `UPLOAD_DIR`.
2. Confirm disk capacity and mounted volume status.
3. Local antivirus adapter is a no-op; production must use a real malware scanning service.

## Frontend can't reach API
1. Confirm `api` service is healthy and listening on port 8080.
2. In Docker, use the frontend Nginx proxy; locally, use Angular's `proxy.conf.json`.
3. Inspect browser developer tools and `docker compose logs frontend`.

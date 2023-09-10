# CNSD bank API

## Local test environment
1. Add `test` profile to spring.
2. Start the application.
3. Go to http://localhost:8080/swagger-ui/index.html for the swagger page.
4. Go to http://localhost:8080/h2-console for the h2 console.
   1. Log in with the following credentials:
      * Username: `sa`
      * Password: `password`

## Local prod environment
1. Run `docker-compose up -d` to create a Postgres container with pg-admin.
2. Add `prod` profile to spring.
3. Start the application.
4. Go to http://localhost:8080/swagger-ui/index.html for the swagger page.
5. Go to http://localhost:5433/browser/ for the pg-admin page.
   1. Log in with the following credentials:
      * Email: `postgres@example.com`
      * Password: `postgres`
   2. Add a new server with the following options:
      * Host name/address: `postgres`
      * Port `5432`
      * Username: `postgres`
      * Password: `postgres`
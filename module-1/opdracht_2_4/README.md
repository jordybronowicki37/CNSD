# CNSD bank API

## Local Development
### Local test environment
1. Add `test` profile to spring.
2. Start the application.
3. Go to http://localhost:8080/swagger-ui/index.html for the swagger page.
4. Go to http://localhost:8080/h2-console for the h2 console.
   1. Log in with the following credentials:
      * Username: `sa`
      * Password: `password`

### Local production environment
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

## Testing
### Unit testing
Jacoco Report
![Jacoco report](/docs/jacoco-report.png)
In the Jacoco report above, you can see that the test coverage has slightly increased 
compared to the results of the integration testing assignment. The majority of the
improvements were in the web.controller and the domain.service folders.

### Mutation testing
PITest report
![PITest report](/docs/pitest-report.png)
The mutation coverage has not changed due to the introduction of the cucumber functional
tests.

## Docker
How to run the project in a container:
1. Create the package:`mvn install`
2. Build the container:`docker build -f Dockerfile -t cnsd-bank .`
3. Start the container: 
   1. Run the stand-alone container with dev profile: `docker run -p 8080:8080 -t cnsd-bank`
   2. Or run the entire prod infrastructure: `docker-compose up -d`
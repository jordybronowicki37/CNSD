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
In the Jacoco report above, you can see that the majority of the tests are testing 
classes from the domain.service and the data.models folder. The coverage percentage is
not yet on the level of 80%, which is the minimum for many software companies. In the 
integration testing fase I hope to increase this number.

### Mutation testing
PITest report
![PITest report](/docs/pitest-report.png)
The majority of the mutations are created in the data.models folder. The classes in
this folder contain Lombok annotations. These annotations automatically generate some
of the standard method implementations that pojo's normally contain. PITest created 
the majority of the mutations in these generated methods from Lombok, which are not
as interesting.

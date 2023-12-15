# Customer Express Microservice

This project is a microservice implementation for customer management using Java, Spring Boot, and a relational database.

![Build](https://github.com/byesfi/customer-express/actions/workflows/pull_request.yml/badge.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=byesfi_customer-express&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=byesfi_customer-express)

## Features

- Retrieve all customers
- Retrieve a specific customer by ID
- Create a new customer
- Update an existing customer
- Delete a customer

## Technologies Used

- Java
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven

## Getting Started

To set up and run the application, follow the steps below:

1. Clone the repository: `git clone https://github.com/byesfi/customer-express.git`
2. Navigate to the project directory: `cd customer-express`
3. Open the project in your favorite IDE (e.g., IntelliJ IDEA, Eclipse)
4. Build and run the Docker containers: `docker-compose up -d`
5. Connect to postgres container: `docker exec -it postgres bash`
6. Connect to postgres: `psql -U byesfi`
7. Create customer database: `CREATE DATABASE customer;`
8. Build the project using Maven: `mvn clean install`
9. Run the application: `mvn spring-boot:run`

The application will start running on `http://localhost:8080`.

The Customer Management System consists of two containers:

- `db` - PostgreSQL database container
- `pgadmin` - PgAdmin container for managing the database

### PostgreSQL Database

The PostgreSQL database container is configured with the following details:

- Username: `byesfi`
- Password: `password`
- Database data is stored in the `postgres` volume.

### PgAdmin

PgAdmin is a web-based tool for managing PostgreSQL databases. It is configured with the following details:

- Default Email: `pgadmin4@pgadmin.org`
- Default Password: `admin`
- Database Server: `db`
- Database Port: `5432`

You can access the following services:

- Customer Management System API: [http://localhost:8080/api/v1/customers](http://localhost:8080/api/v1/customers)
- PgAdmin: [http://localhost:5050](http://localhost:5050)

## API Endpoints

- `GET /api/v1/customers` - Retrieves all customers.
- `GET /api/v1/customers/{customerId}` - Retrieves a specific customer by ID.
- `POST /api/v1/customers` - Creates a new customer.
- `PUT /api/v1/customers/{customerId}` - Updates an existing customer.
- `DELETE /api/v1/customers/{customerId}` - Deletes a customer.

## Documentation

Detailed documentation of the API endpoints and request/response structures can be found in the [API Documentation](/docs/api.md) file.

## Contributing

Contributions are welcome! If you find any issues or have suggestions for improvements, please create a new issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

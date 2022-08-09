# Shift Management API

This is an employee shift management application built using Spring Cloud. Users
can perform CRUD operations on employees, stores, and shifts along with sending
out weekly schedules and shift updates via the email associated with the employee(s).

## Technology Used

1. Java 17
2. Maven
3. SpringBoot
4. Spring Cloud
5. PostgreSQL
6. Freemarker
7. Docker

## Installation and Getting Started

1. Clone to local repo
2. In GitHub Secrets or an external environment variable file you need to declare the following variables
pertaining to your email username and password. This application has been set up to use Gmail as an email provider
   1. SPRING_MAIL_USERNAME
   2. SPRING_MAIL_PASSWORD

* Note: For Gmail, the password is referring to an app password. For instructions on how to generate an app password 
click [here](https://www.data-recovery-solutions.com/blog/how-to-generate-gmail-app-password-step-by-step-guide/)

3. Package jar file and build Docker images
````
./pack-images.sh
````

## Running Application with Docker

First ensure that you have Docker installed. See the following link for details

   * https://docs.docker.com/get-docker/

Then start the containers using Docker Compose

````
docker-compose up
````

The containers can be stopped with 

````
docker-compose down
````

## Testing

The application can be tested locally by running

````
./mvnw test
````

Integration tests can also be performed using the provided Postman collection.

## Details

### API Gateway

The application uses the Spring Cloud API Gateway to route incoming HTTP request to the appropriate
service. The gateway implements load balancing to evenly distribute requests to a service amongst
all instances of said service. 

### Services

 See the table below for valid routes for reaching each service along with a brief description.

| Service  | Description                                                                  | Route               |
|----------|------------------------------------------------------------------------------|---------------------|
| Address  | Company store information                                                    | /api/v1/address/**  |
| Employee | Company employee information                                                 | /api/v1/employee/** |
| Shift    | Create, read, and update employee shifts                                     | /api/v1/shift/**    |
| Email    | Company can send weekly shift schedules and employee shift updates via email | /api/v1/email/**    |

````
For more information on the available endpoints for each service visit http://localhost:8080/swagger-ui.html 
once the application is started and using the search bar provided by Swagger enter the name of the service in the form 
of "/v3/api-docs/{route}". For instance, to view all endpoints for the address service you can search
"/v3/api-docs/api/v1/address".
````

### Zipkin Distributed Tracing

A Zipkin server is implemented to handle distributed tracing. This server can be accessed using
port 9411.

### Eureka Discovery Service

To view all services currently up the Eureka discovery server can be reached on port 8761.

## Updates

This project was meant to be a starting point for a larger project that encompasses more services pertaining to 
employee management for a company that may have multiple stores, or places of business. However, it is in need of an
architecture redesign beforehand. Currently, the shift and email services must rely on OpenFeign clients to make HTTP 
requests to other services repeatedly. This, in my opinion, tightly couples the services and leads to a brittle system
overall. The solution to the redesign for version 2.0 is to implement the command query responsibility segregation
(CQRS) and event sourcing design patterns using a PostgreSQL database for creating and updating entities, a MongoDB
database with a unified view of shifts, employee, and store information and a Kafka event bus to maintain 
synchronization between the tables. 

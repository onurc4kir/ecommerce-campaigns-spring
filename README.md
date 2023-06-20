# E-commerce User-Related Campaigns With Spring Boot

This project is a web application that lists campaigns and displays advantageous campaigns at the top when a user logs in and include some CRUD operations on them.

## Requirements

- Java 19
- Spring Boot 3.1
- PostgreSQL

## Installation

1. Clone the project repository to your computer.

`git clone https://github.com/onurc4kir/ecommerce-campaigns-spring.git`

2. Install PostgreSQL and configure the connection settings in `application.properties` file.

```
spring.datasource.url=jdbc:postgresql://localhost:5432/db_name 
spring.datasource.username=username
spring.datasource.password=password
```

3. Navigate to the project directory and compile/run the project using the following command:

```mvn spring-boot:run```

4. Access the application at `http://localhost:8080`.

## Usage

- Use the `GET /campaigns` endpoint to list the campaigns.
- Use the `POST /login` endpoint to log in as a user.
- Use the `POST /users` endpoint to create a new user.
- Use the `PUT /users/{id}` endpoint to update users.
- Use the `DELETE /users/{id}` endpoint to delete users.
- Use the `POST /campaigns` endpoint to create a new campaign.
- Use the `PUT /campaigns/{id}` endpoint to update campaigns.
- Use the `DELETE /campaigns/{id}` endpoint to delete campaigns.

Campaigns are queried by profile-match if the user logged in. 

### Roles
There are 2 types of roles used for authorization ROLE_ADMIN and ROLE_USER. CRUD for the role and profession requires ROLE_ADMIN. Also ROLE_ADMIN is required for adding or updating a campaign. 

You can access detailed API documentation at `http://localhost:8080/swagger-ui.html` while the application is running.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for more details.

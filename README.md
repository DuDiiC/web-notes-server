### A sample application showing the use of the Spring platform in practice. I mainly use Spring Boot, Spring Security, Spring Framework, Spring Data.

Application for managing user notes. Implemented functionalities:

- Spring Security:
  
  - [x] user management using the `UserDetails` class extension
  - [x] registration
  - [x] stateless session - verifying users using JWT
  - [x] email verification during registration
  - [x] endpoint security for individual roles and users
  - [x] passwords stored in the database in a hash form

- Spring Data:

  - [x] two tables: `User` and `Note` with a one-to-many relationship as example of mapping
  - [x] repositories implement `JpaRepository<T, ID>` with own queries (without problems, for example N+1 query problem)

- Spring Framework:

  - [x] RESTful API for `User` and `Note` with DTO classes (conversion using `ModelMapper`)
  - [x] controllers with implemented pagination, sorting and security, using services
  - [x] full error handling for API

- others:

  - [x] implemented [swagger](https://swagger.io/) for API documentation
  - [ ] implementation of tests with JUnit and Mockito
  - [ ] use of the Value-Object pattern for entities

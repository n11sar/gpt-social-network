# Social Media Application

## Description

This social media application is a simple RESTful API built using Spring Boot, Hibernate, and PostgreSQL. It allows users to create and view posts, follow other users, and like posts.

## Features

- **User Management:** Users can follow and unfollow other users.
- **Post Creation and Viewing:** Users can create new posts and view existing ones. Each post includes a title, body, and author information.
- **Post Interaction:** Users can like posts.

## Requirements

- Java JDK 11 or later
- PostgreSQL
- Gradle

## Setup

Before running the application, ensure that you have a PostgreSQL database set up and accessible.

### Database Configuration

Update the `src/main/resources/application.properties` file with your PostgreSQL database details:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/yourDatabase
spring.datasource.username=yourUsername
spring.datasource.password=yourPassword
```

### Building the Application

To build the application, navigate to the project's root directory and run the following command:

```bash
gradle build
```

This command compiles the application and runs any unit tests.

### Running the Application

After successfully building the application, you can run it using the following command:

```bash
gradle bootRun
```

The application will start and run on `http://localhost:8080`.

## API Endpoints

The application exposes several RESTful endpoints:

- `POST /users/follow`: Follow a user.
- `POST /users/unfollow`: Unfollow a user.
- `POST /posts`: Create a new post.
- `GET /posts/{postId}`: Get details of a specific post.
- `POST /posts/{postId}/like`: Like a post.

## Testing

To run the test suite, use the following command:

```bash
gradle test
```

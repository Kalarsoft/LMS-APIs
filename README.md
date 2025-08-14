# LMS-APIs

This project was created using the [Ktor Project Generator](https://start.ktor.io).

## ARCHIVED
Due to [the recent changes to GitHub's indepenence](https://www.theverge.com/news/757461/microsoft-github-thomas-dohmke-resignation-coreai-team-transition), I have moved development of this project to an open source Git hosting service.  
[New version here](https://gitea.com/NickKalar/LMS-APIs.git)

## Features

Here's a list of features included in this project:

| Name                                                                   | Description                                                                        |
|------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| [Default Headers](https://start.ktor.io/p/default-headers)             | Adds a default set of headers to HTTP responses                                    |
| [Routing](https://start.ktor.io/p/routing)                             | Provides a structured routing DSL                                                  |
| [Swagger](https://start.ktor.io/p/swagger)                             | Serves Swagger UI for your project                                                 |
| [Authentication](https://start.ktor.io/p/auth)                         | Provides extension point for handling the Authorization header                     |
| [Authentication JWT](https://start.ktor.io/p/auth-jwt)                 | Handles JSON Web Token (JWT) bearer authentication scheme                          |
| [Content Negotiation](https://start.ktor.io/p/content-negotiation)     | Provides automatic content conversion according to Content-Type and Accept headers |
| [kotlinx.serialization](https://start.ktor.io/p/kotlinx-serialization) | Handles JSON serialization using kotlinx.serialization library                     |
| [Postgres](https://start.ktor.io/p/postgres)                           | Adds Postgres database to your application                                         |

## Required Files
### src/main/resources/application.yaml
Required fields:
ktor:
  application:
    modules:
      -codes.kalar.ApplicationKt.module
  deployment:
    port: 8080
postgres:
  url: "jdbc:postgresql://<url to database>"
  user: <username>
  password: <password>

## Building & Running

To build or run the project, use one of the following tasks:

| Task                          | Description                                                          |
|-------------------------------|----------------------------------------------------------------------|
| `./gradlew test`              | Run the tests                                                        |
| `./gradlew build`             | Build everything                                                     |
| `buildFatJar`                 | Build an executable JAR of the server with all dependencies included |
| `buildImage`                  | Build the docker image to use with the fat JAR                       |
| `publishImageToLocalRegistry` | Publish the docker image locally                                     |
| `run`                         | Run the server                                                       |
| `runDocker`                   | Run using the local docker image                                     |

If the server starts successfully, you'll see the following output:

```
2024-12-04 14:32:45.584 [main] INFO  Application - Application started in 0.303 seconds.
2024-12-04 14:32:45.682 [main] INFO  Application - Responding at http://0.0.0.0:8080
```

From here, navigate to http://0.0.0.0:8080/swagger for API documentation.

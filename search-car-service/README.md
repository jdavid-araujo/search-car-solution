## About
The project has as finality to verify how much time a vehicle stay on a region

## Technologies
  * Java API for bean validation
  * Jpa/Hibernate and Spring Data
  * Spring Boot
  * Rest with Spring
  * Error Handling for REST with Spring
  * Redis (caching)
  * Docker Compose
  * Kubernetes
  * Swagger
  * Unit Test
  * Internationalization in Spring Boot
  * Gradlew

## How to run

### Local Environment
* Create a postgres and redis instances
* Execute the project

### Containers
* Create a jar file of the project

#### Docker-compose
* Go to main directory of the project search-car-service
* Execute docker-compose up to create an instace of the application, postegres and redis
* Execute docker-compose down to destry the instances

#### Kubernetes
* Go to k8s folder of the search-car-solution and follow the readme.md
* Go to k8s of the search-car-service
* Execute kubectl apply -f  app-deployment.yaml
* Execute kubectl apply -f  app-service.yaml
* Execute kubectl apply -f  app-hpa.yaml

## Documentantion

The Swagger can be accessd by http://localhost:8080/swagger-ui/index.html#/

## Project URL

`localhost:8080/v1`


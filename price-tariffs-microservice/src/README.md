# Price Tariffs challenge

## Overview
Inditex ecommerce database contains a table named Prices with the different final prices (PVP) and price tariffs
applicable to a brand product on different range dates.

This table stores following information:
- Brand ID. (e.g. ZARA)
- Start and end dates: The date range for which the price tariff applies.
- Price List: The price tariff ID.
- Product ID. (e.g. 35455)
- Priority: Used for resolving conflicts between different price tariffs. The higher the value, the more priority has the price tariffs over others applicable price tariffs.
- Price: The final price (PVP).
- Currency: The currency ISO code.

The challenge consists of developing an application or service using Spring Boot that
exposes a REST endpoint with following criteria:

Parameters:
- Date
- Product ID
- Brand ID

Result:
- Price Tariff ID
- Product ID
- Brand ID
- Range dates for which the price tariff applies
  - Question: Is it the range date stored in DB or has to consider other price tariffs?
- Final price


## Guidelines

## Running
There are multiple different modes for running this Spring Boot application.
First of all, we need to build our project using maven:
> mvn clean package

Second, we can use one of these modes:
1. Standalone Java application
   > D:\Applications\Java\jdk-20.0.1\bin\java -jar .\target\price-tariffs-1.0-SNAPSHOT.jar
2. Docker container
   1. Create docker image
   > docker build --tag=price-tariffs:latest .
   2. Start a new docker container (on a specific port) using the created docker image. 
   > docker run -p8888:7070 price-tariffs:latest
3. Docker composer
   1. > docker-compose build
   2. > docker-compose up -d

NOTE: The Spring Boot application is configured to run on port 7070, but when running on a docker container, we specify the port mapping, which can be the same or different.
In our command above, the port 7070 is mapped to port 8888.
This port mapping is useful if we want to run the same Spring Boot application in more than one docker container.

## Testing
Multiple tests have been created for testing the correct behavior of the microservice functionality.
These are the tests classified by types and which artifact is testing:

- Unit Tests: These tests can be identified by the "Test" suffix.
- Integration Tests: These tests can be identified by the "IT" suffix.
- Performance Tests: ...

- Usage of Mockito test framework, ...

## Mandatory Challenge Tests
The 5 mandatory tests for this challenge are developed in classes:
- PriceTariffControllerForChallengeWebIT: class containing the tests definitions. 
- PriceTariffControllerForChallengeNativeWebIT: class that loads and run the tests using the SpringData JPA native query service implementation.
- PriceTariffControllerForChallengeJpqlWebIT: class that loads and run the tests using the SpringData JPA JPQL query service implementation.

### Coverage
I have configured the project to use JaCoCo for measuring code coverage by both Unit and Integration tests.
Configuration is done via the Maven pom.xml file, which generates 3 reports (one considering only Unit tests, other for Unit tests, and a third one merging both types of tests).
Reports can be found at paths:
- target/site/jacoco-unit-test-coverage-report
- target/site/jacoco-integration-test-coverage-report
- target/site/jacoco-merged-test-coverage-report

Unit test coverage report is generated in the XXX Maven build phase, so it can be generated using following command:
> mvn test

As for the Integration test coverage report, this is generated in the verify phase, so the command would be:
> mvn verify

The Merged test coverage report is generated in the verify phase too.

On the other hand, JaCoCo has been configured to make the build fail if one of these rules fail to meet:
- Each class has a line coverage ratio of at least 80%.
- The bundle (project) has a complexity coverage ratio of at least 90%.  

## Exception Handling
A global exception handling has been implemented using the @ControllerAdvice annotation on the GlobalExceptionHandler class.
This class will catch all exceptions thrown by our controller, including instances of PriceNotFoundException, InvalidBrandException and InvalidProductException.
For any other exceptions, a common response is generated.

## Implementation details
Microservice can be configured from the application.properties file.
At the moment, this file contains the port where the microservice will run,
database connection settings and log settings.

## Microservice(s)
This challenge has been developed as a Spring Boot microservice.

## Layers
The microservice has 3 layers:
1. Controller layer
2. Service layer
3. Database layer

### Controller layer
The Controller layer is implemented via a REST Controller, and expose a single endpoint:
HTTP Method: GET
HTTP Path: /price-tariffs/find

### Service layer
...

### Database layer
An embedded H2 database has been used in this implementation.

As any in-memory database, this is regenerated everytime it is started.
In this sense, this database is populated with example data during the microservice startup.
The database content can be found at the import.sql file.

Despite the mandatory test scenarios are based only on brand ZARA and product 35455, more brands and products have been added.

In regard to the price tariffs, only the four required for the test scenarios have been added.

Following entities have been created:
- Brand: Represents a brand within the Inditex group.
- Product: Represents a product sold by Inditex.
- Price: Represents a price tariff for a specific product valid during a specific date range. As two different products from different brands can share the same ID, the brand ID is added to it. Also, a priority number has been added to resolve conflict when more than one price tariff applies to the same product and brand on a specific date.
- Currency

For simplicity, the only information we are storing for each Brand are its ID and name.
The same applies to Products, but this also contains a list of prices associated to each product.
Currency has been implemented as an enum with 3 possible values EUR, GBP and USD, though EUR is the only needed for our tests.

Each entity has its own repository. Both Brand and Product repositories have the default Spring Data repository methods (e.j. findById, findAll, ...)
However, a couple of custom methods have been added to the Price repository.
Why different methods? Basically, I have developed multiple implementations of the logic to find the applicable price tariff for a given request.
1. JPQL Query: Returns the list of applicable price tariffs, which has to be filtered later on by the caller (service).
2. Native Query: Returns an Optional object wrapping the applicable price tariff or an empty one if none is found. 

## Postman
A Postman collection has been added to the postman folder.
This collection contains multiple HTTP requests to test the REST controller under different scenarios (including the mandatory test scenarios).

## Decisions made
- I have declared date, productId and brandId attributes in FindPriceTariffRequest class with type String. They could be declared as Long for the IDs and for Date (in case of sending as timestamp).
- Though this microservice only provides a GET endpoint, I have decided to pass parameters as a JSON in the body request. A different, and more common approach would have been passing them as query parameters.
- The easiest way to implement the Strategy Pattern in PriceTariffFinderJpqlService and PriceTariffFinderNativeService was not using the @Service annotation and creating those instances from the Spring Boot app configuration. Hence, we had to pass the PriceRepository as parameter in their constructor.

## Improvements
- Add performance tests (for example using jMeter)
- Add other DB Engines (for example MongoDB or MySQL)
- Create Docker container with the microservice and a database (for example MongoDB or MySQL).
- Create another microservice so it interacts with each other.
- Configure Spring Boot Actuator?
- Create separate exceptions to differentiate when a parameter has a wrong value or there is no entity/record in database with the given value (ID).

## Design Patterns
We have used different design patterns along the project:

### Strategy pattern
There are 2 implementations of the PriceTariffFinderServiceI interface, one that searches applicable price tariffs using a native query (PriceTariffFinderNativeService) and other using a JPQL query (PriceTariffFinderJpqlService).
The PriceTariffService will use one of those implementations based on the configured Bean in the AppConfig class, which in turn is based on priceTariffFinderService application property.
This property is defined in the application.properties file, and can have one of these values: "native" or "jpql".

### Singleton
A ProductHelper class has been created which only allows one single instance of that class.
It contains a static field named INSTANCE which can only be accessed by the getInstance() static method.
This method has also been marked as synchronized to avoid more than one thread entering the method and creating an instance in case it wasn't before.

### Template Method
PriceTariffAbstractService and PriceTariffService classes implement the Template Method design pattern.
PriceTariffAbstractService defines the template for the findPrice method, which uses the abstract checkProductExists, checkBrandExists and searchPriceTariff abstract methods that are implemented by the PriceTariffService.
Hence, PriceTariffService is implementing the gaps used by the template method.

## Software versions
Java version 16 has been configured at project level.

## Lombok
I am using Lombok to generate getters, setters, constructors and builder pattern on several DTOs.
For example:
- FindPriceTariffRequest: We need getters and a default constructor. Setters are added for testing purposes.  
- FindPriceTariffResponse: I don't need setters because I am building instances using the builder pattern.

## Actuator
We have added Spring Boot actuator to the microservice, and it is accessible via the /actuator endpoint.
For example, if microservice is running in localhost at port 7070, the URL would be: 
> http://localhost:7070/actuator

## Questions

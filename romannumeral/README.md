## Application Overview

A web application that provides a REST web service end point which accepts URI in following format and returns the Roman numeral representation: 

Example: http://localhost:8080/romannumeral?query={integer}
Supported integer range: 1<={integer}<=3999

## How to build the application
Step 1. Open a Terminal window and change to a directory for cloning the project.

```
cd /myproject
```

Step 2. Clone the project from GIT repository.

```
$git clone https://github.com/yzhou0407/romannumeral.git
```

Step 3. Change directoy to the folder under which project contents are located.

```
$cd /mygitproject/romannumeral/romannumeral
```

Step 4. Build and install the application.
	Note: During the installation phase, integration tests will be executed. Meanwhile, a docker image named as 'romannumeral' will be created along with the install process.

```
$mvn clean install
```

Step 5. Run the application in 2 different ways:

   Option1: To run the application in current local machine, just simply run command: 
   
```
    $java -jar ./target/romannumeral-0.0.1-SNAPSHOT.jar
```
   
   Option2: To run the application in a docker container:
   * Check there is a docker image being created with name 'romannumeral' by:
   
```
    $docker images
   
```
   
   * Run a container using docker image 'romannumeral' by:
   
```
    docker run -p 8080:8080 romannumeral
```
   	
Step 6. Once application is started, use either web browser or other tool like postman to send request.

```
http://localhost:8080/romannumeral?query=1
```

## Testing Methodology

There are integration JUnit test cases being added into the project and are one important step of installing the application. The test cases simulates the HTTP GET request and verify the response result by checking response status, as well as the response payload.

## DevOps capabilities
By utilizing Spring Boot Actuator, the application is configured to provide metrics for monitoring the environment. The metrics can be accessed through http endpoints at:

http://localhost:8080/actuator/health
http://localhost:8080/actuator/prometheus

Other than built-in metrics, two customized metrics for total request count(romanNumeralService_total_request_total) and error request count (romanNumeralService_error_request_total) for romannumeral service are added. 

Meanwhile, there will be log files generated once application starts running.
* Log files are located under the project's root folder: 

  `<projectroot>/logs`

* Two separate log files will be generated in order to collect logs in different level.

  romannumeral-logger.log: trace level logs
  
  romannumeral-severe-logger.log: warn level logs
* Log files will be rolled over daily when the file reaches 10 MegaBytes size.
  
  Archived log files can be accessed under folder:
  `<projectroot>/logs/archived`

## Packaging layout:
The application is based on Spring Web MVC framework and is built with Spring Boot.
* Application's source codes are located in:

```
<projectroot>/src/main/java/
com.springboot.romannumeral:
	root package where RomanNumeralApplication.java is located. 

com.springboot.romannumeral.controller:
	Package for RestController where mapping between end point and backend service is defined.

com.springboot.romannumeral.model:
	Package for Model layer(RomanNumeral.java in which returned json payload fields are stored in backend.)

com.springboot.romannumeral.service:
	Package for RestService layer(RomanNumeralService.java contains all business logic for this application.)

com.springboot.romannumeral.exception:
	Package contains several java class files related to exception handling.)
```

* Application's integration test is located in:

```
<projectroot>/src/test/java
com.springboot.romannumeral:
	package contains RomanNumeralApplicationTest.java designed for integration test.
```

* Dockerfile

 `<projectroot>/Dockerfile`

This file will be used to create docker image when run mvn install command.

* application.properties

Configuration related to spring boot actuator is done through application.properties files.

`<projectroot>/src/main/resources/application.properties`

* logback-spring.xml

Logging related settings are configured in logback-spring.xml. In the application, also logback logger is used to generate logs while application runs.

`<projectroot>/src/main/resources/logback-spring.xml`

## Depenency:
* JDK 1.8 or later
	https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
* When build the project, it will automatically create a docker image using the Dockerfile located in <projectroot>/Dockerfile. 
	Need to install docker on the local machine in order to successfully install the application.
	https://docs.docker.com/get-docker/
## Application Overview

A web application that provides a REST web service end point which accepts URI in following format and returns the Roman numeral representation: 

Example: http://localhost:8080/romannumeral?query={integer}
Supported integer range: 1<={integer}<=3999

## How to build the application
1. Open a Terminal window and change to a directory for cloning the project.
Example:
cd /myproject
2. Clone the project from GIT repository.
git clone https://github.com/yzhou0407/romannumeral.git
3. Change directoy to the folder under which project contents are located. 
cd /mygitproject/romannumeral/romannumeral
4. Build and install the application.
mvn clean install
Note: During the installation phase, integration tests will be executed. Meanwhile, a docker image named as 'romannumeral' will be created along with the install process.
5. Run the application in 2 different ways:
   a. To run the application in current local machine, just simply run the following command:
   java -jar ./target/romannumeral-0.0.1-SNAPSHOT.jar
   b. To run the application in a docker container:
   	b.1) Check there is a docker image being created with name 'romannumeral'. Use following command to check.
   		 docker images
    b.2) Run a container using docker image 'romannumeral'.
     	 Example:
         docker run -p 8080:8080 romannumeral
6. Once application is started, use either web browser or other tool like postman to send request.
Example:
http://localhost:8080/romannumeral?query=1

## Testing Methodology

There are integration JUnit test cases being added into the project and are one important step of installing the application. The test cases simulates the HTTP GET request and verify the response result by checking response status, as well as the response payload.

## DevOps capabilities
By utilizing Spring Boot Actuator, the application is configured to provide metrics for monitoring the environment. The metrics can be accessed through http endpoints at:

http://localhost:8080/actuator/health
http://localhost:8080/actuator/prometheus

Other than built-in metrics, two customized metrics for total request count(romanNumeralService_total_request_total) and error request count (romanNumeralService_error_request_total) for romannumeral service are added. 

Meanwhile, there will be log files generated once application starts running.
1. Log files are located under the project's root folder:
   <projectroot>/logs
2. 2 seperate log files will be generated in order to collect logs in different level.
   a) romannumeral-logger.log: trace level logs 
   b) romannumeral-severe-logger.log: warn level logs
   Log files will rollover daily when the file reaches 10 MegaBytes size. And the older log files can be accessed under folder:
   <projectroot>/logs/archived

## Packaging layout:
The application is based on Spring Web MVC framework and is built with Spring Boot.
1. Application's source codes are located in:
<projectroot>/src/main/java/
	com.springboot.romannumeral
		RomanNumeralApplication.java -> Application's entry point
	com.springboot.romannumeral.controller
		RomanNumeralController.java  -> RestController to map end point /romannumeral to backend service.
	com.springboot.romannumeral.model
		RomanNumeral.java 			 -> RomanNumeral stores input and output information. It will be returned as the body of ResponseEntity
	com.springboot.romannumeral.service
		RomanNumeralService.java     -> RestService in which logics for URI request parameter validation and decimal integer to Roman numeral conversion exist.
	com.springboot.romannumeral.exception
		ErrorDetails.java 			 -> ErrorDetails stores detail information related to the error. It will be returned as the body of ResponseEntity when exception happens.
		GlobalExceptionHandler.java  -> Global exception handler defined specific for /romannumeral end point.
		InputIntegerOutOfRangeException.java -> Customized runtime exception.
		InvalidIntegerFormatException.java   -> Customized runtime exception.
		NoInputException.java                -> Customized runtime exception.

2. Application's integation test is located in:
<projectroot>/src/test/java
	com.springboot.romannumeral
		RomanNumeralApplicationTest.java -> 12 Integration test cases are added to cover positive/negative cases.

3. Dockerfile
<projectroot>/Dockerfile
	This file will be used to create docker image when run mvn install command.

4. application.properties
Configuration related to spring boot actuator is done through application.properties files.
<projectroot>/src/main/resources/application.properties

5. logback-spring.xml
Logging related settings are configured in logback-spring.xml. In the application, also logback logger is used to generate logs while application runs.
<projectroot>/src/main/resources/logback-spring.xml

## Depenency:
1. JDK 1.8 or later
   https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
2. When build the project, it will automatically create a docker image using the Dockerfile located in <projectroot>/Dockerfile. So need to install docker on the local machine in order to successfully install the application.
	https://docs.docker.com/get-docker/
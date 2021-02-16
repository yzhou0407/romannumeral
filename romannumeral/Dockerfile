# Alpine Linux with OpenJDK JRE
FROM openjdk:8-jre-alpine

VOLUME /tmp

# Copy jar file
COPY target/*.jar /romannumeral.jar

# run the app
ENTRYPOINT ["java","-jar","/romannumeral.jar"]
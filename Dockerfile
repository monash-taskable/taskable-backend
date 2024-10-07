FROM openjdk:21-jdk-slim

# set working dir
WORKDIR /app

# copy the jar file from local to container
COPY target/*.jar app.jar

# port app runs on
EXPOSE 8080

# runs application
ENTRYPOINT ["java", "-jar", "app.jar"]

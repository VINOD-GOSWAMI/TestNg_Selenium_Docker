# Use Maven image for building and running the tests
FROM maven:3.8.6-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /home/codaPayment

# Copy POM.xml and download dependencies
COPY pom.xml .

# Copy project source files and testNg.xml
COPY src ./src
COPY testNg.xml .

# Use entry point to execute tests via docker-compose command
ENTRYPOINT ["mvn", "test", "-DisRemote=true"]


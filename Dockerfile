# Use Maven image for building and running the tests
FROM maven:3.8.6-openjdk-11 AS build

# Set the working directory inside the container
WORKDIR /home/codaPayment

# Copy POM.xml and download dependencies
COPY pom.xml .

# Download dependencies and plugins (go offline) && Compile the project and run tests and store them in the Docker cache
RUN mvn dependency:go-offline \
     && mvn test-compile -B

# Copy project source files and testNg.xml
COPY src ./src
COPY testNg.xml .

# Use entry point to execute tests via docker-compose command
ENTRYPOINT ["mvn","test","-DisRemote=true"]
CMD []

# Use the official image as a parent image.
FROM openjdk:latest

# Set the working directory.
WORKDIR /usr/src/app

# Copy the file from your host to your current location.
COPY trading-0.0.1-SNAPSHOT.jar .

# Run the command inside your image filesystem.
#RUN mvn install

# Add metadata to the image to describe which port the container is listening on at runtime.
EXPOSE 8080

# Run the specified command within the container.
CMD [ "java", "-jar", "trading-0.0.1-SNAPSHOT.jar"]

# Copy the rest of your app's source code from your host to your image filesystem.
#COPY . .

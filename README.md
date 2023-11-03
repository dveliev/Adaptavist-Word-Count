# Word Count Application

The Word Count Application is a Spring Boot-based web service that allows you to upload a text file and get a word count analysis of its contents. The application provides a simple API to process text files and returns word count statistics.

## Prerequisites

- Java 17
- Gradle
- IDE

## Getting Started
1. Building the application happens with the following command
```bash 
 ./gradlew build 
 ```
2. Running the tests
```bash 
 ./gradlew test 
 ```
3. Running the application
```bash 
./gradlew bootRun
 ```

## Usage
To use the Word Count Application:

1. Ensure the application is running
2. Open Postman to interact with the application 
3. Use the `/word-count/process` endpoint to upload the text file and get the word count statistics. The API accepts a `multipart/form-data` POST request with a `file` field containing the text file to be analyzed.
4. Review the response, which will include word count statistics or error message if any issue occurs.


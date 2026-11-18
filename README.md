# Student Academic Management REST API

A Spring Boot RESTful web service built for managing student academic records in memory without requiring an external database.

## Features
- **Project Structure**: Follows standard 3-tier layering (`com.kiet.student`, `model`, `controller`).
- **Application Startup Logging**: Uses Spring Boot logger with `CommandLineRunner` to log status events.
- **In-Memory Data Storage**: Thread-safe `List` holding student profiles.
- **REST Endpoints**:
  - `GET /students` - Fetch all student records.
  - `GET /students/{studentId}` - Fetch single student by numeric ID.
  - `POST /students` - Create a new student record via JSON payload.
  - `PUT /students/{studentId}` - Update existing student details.
  - `DELETE /students/{studentId}` - Remove student record.
  - `GET /students/search?branch={branch}&year={year}` - Query students by branch and academic year.
  - `GET /students/topper?cgpa={cgpa}` - Filter students having CGPA greater than or equal to a given score.

## Tech Stack
- Java 17+ (JDK 25 compatible)
- Spring Boot 3.3.5
- Spring Web MVC
- Apache Maven

## Running the Application
```bash
./run.sh
```
Or directly with Maven:
```bash
mvn spring-boot:run
```
The server listens on `http://localhost:8080`.

## Prompts Used During Development
1. **Project Setup & Model Definition**:
   > "Configure a Maven-based Spring Boot 3 web application running on port 8080 under package com.kiet.student. Create a Student model containing studentId, name, email, branch, year, and cgpa with constructors, getters, and setters."

2. **Runner & Logger Lifecycle Configuration**:
   > "Implement CommandLineRunner inside the Spring Boot application class using SLF4J Logger to log startup messages indicating system initialization, data loading, and API readiness."

3. **REST Controller & Endpoint Implementation**:
   > "Build a REST controller mapped at /students with an in-memory thread-safe list pre-populated with initial records. Implement CRUD operations with GET, POST, PUT, DELETE, and query parameter search endpoints for branch/year and CGPA filtering, returning appropriate JSON messages when records are missing."

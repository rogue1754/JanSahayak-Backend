JanSahayak

JanSahayak is a government scheme discovery platform that helps users
find government schemes based on their eligibility and personal
information.

Features

-   Eligibility-based government scheme matching
-   Rule-based eligibility evaluation
-   Scheme ranking based on matching criteria
-   Government scheme and required-document information
-   REST APIs for frontend integration
-   PostgreSQL database
-   Structured scheme data ingestion

Tech Stack

Backend

-   Java
-   Spring Boot
-   Spring Data JPA
-   PostgreSQL
-   Maven

Frontend

The frontend is developed and hosted separately by the frontend team.

How It Works

User eligibility details are sent from the frontend to the backend. The
backend evaluates the details against stored scheme eligibility rules,
ranks matching schemes, and returns the results to the frontend.

Frontend ↓ Spring Boot REST API ↓ Eligibility Matching ↓ Eligibility
Rule Evaluation ↓ Scheme Ranking ↓ PostgreSQL

Main API Endpoints

Match Eligible Schemes

POST /api/eligibility/match

Get All Schemes

GET /api/schemes

Get Scheme by ID

GET /api/schemes/{id}

My Contribution

I developed the backend of JanSahayak, including:

-   Spring Boot REST APIs
-   Eligibility matching and rule evaluation
-   Scheme ranking logic
-   PostgreSQL database integration
-   JPA entities and repositories
-   Scheme data ingestion
-   Backend integration with the frontend

Backend Structure

Controller DTOs Entities Enums Repository Service resources/data

Local Setup

1.  Clone the repository.
2.  Configure PostgreSQL.
3.  Create the required database.
4.  Configure the database connection in application.properties.
5.  Run the application:

./mvnw spring-boot:run

The backend will start on the configured Spring Boot port.

Purpose

JanSahayak aims to simplify the process of discovering government
schemes by matching citizens with schemes based on their eligibility
criteria.

Project

JanSahayak was developed as a complete application with a separately
developed and hosted frontend and a Spring Boot backend.

Backend contribution: Java / Spring Boot / PostgreSQL

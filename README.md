# Job Vault Project

**Job Vault** is an application designed to streamline recruitment processes and efficiently manage candidate information.
It provides functionalities for saving candidate details, filtering candidates, and managing CV files.
With a user-friendly interface and a robust backend, it aims to simplify the tasks of employers and recruiters.

---

## **Purpose of the Application**

This application aims to improve efficiency in recruitment processes by addressing the following needs:

1. **Candidate Management**:
    - Provides a systematic way to record and organize candidate information during recruitment.
    - Enables quick access to candidate details.

2. **Filtering and Search**:
    - Allows filtering of candidates based on specific criteria (position type, military status, notice period, etc.) for quick access to suitable candidates.

3. **CV Management**:
    - Allows uploading, storing, and downloading CV files securely and efficiently.
    - Ensures organized and secure access to CVs.

4. **Integration Ease**:
    - Offers seamless integration between frontend and backend.
    - Provides easy access to API endpoints via the Swagger interface.

---

## **Project Structure**

```plaintext
job-vault/
├── api/                    # Backend code
├── web/                    # Frontend code
├── docker-compose.yml      # Docker Compose configuration
└── README.md               # General project description

```

# Job Vault API

The Job Vault API enables users to manage candidate information, upload CV files, and perform filtering operations. 
It is developed using Spring Boot and the H2 database.

## Features
- Add, update, and delete candidates
- Filter candidates
- Upload and download CV files
- Access API endpoints via the Swagger interface

---

## Requirements
- **Java 17** (Amazon Corretto recommended)
- **Maven 3.8+**
- **Docker** and **Docker Compose**
- **H2 Database**

---

## How to Run?

### 1. **Run in Development Mode with Maven**
1. Install dependencies:
   ```bash
   mvn clean install
2. Start the application:
   ```bash
   mvn spring-boot:run
3. Access the API endpoints via the Swagger interface:
   ```bash
   http://localhost:8080/swagger-ui/index.html

# API Endpoint List

| **HTTP Method** | **Endpoint**                             | **Description**                                                           |
|-----------------|------------------------------------------|---------------------------------------------------------------------------|
| **GET**         | `/api/candidates/{id}`                   | Retrieve the details of a specific candidate                              |
| **GET**         | `/api/candidates`                        | List all candidates with pagination                                       |
| **POST**        | `/api/candidates`                        | Add a new candidate (including CV)                                        |
| **PUT**         | `/api/candidates/{id}`                   | Update the details of a specific candidate (including CV)                 |
| **DELETE**      | `/api/candidates/{id}`                   | Delete a specific candidate                                               |
| **GET**         | `/api/candidates/filter`                 | Filter candidates by position type, military status, and notice period    |
| **GET**         | `/api/candidates/download-cv/{fileName}` | Download the CV file of a specific candidate                              |



# Job Vault Web

Job Vault Web is a frontend application that allows users to manage candidate information, upload CV files, and perform filtering operations. 
It is developed using Angular and Ionic.

## Features
- Add, update, and delete candidates
- Filter candidates
- Upload and download CV files

## Requirements
- **Node.js 18+**
- **NPM 8+**
- **Ionic CLI**
- **Docker** ve **Docker Compose**

## How to Run?

### 1. **Run in Development Mode with Ionic**
1. Install dependencies:
   ```bash
   npm install
2. Start the application:
   ```bash
   ionic serve
3. Open your browser and navigate to:
   ```bash
   http://localhost:8100


#  Usage Scenario
1. The user logs into the system. 
2. Adds a new candidate through the candidate addition screen. 
3. Filters the candidate list to find suitable candidates. 
4. Views or downloads candidate details and CVs. 
5. Updates or deletes candidate information if necessary.  

### **Build ve Deploy**
1. Start the entire system using Docker Compose:
   ```bash
   docker compose up --build
2. Access the backend via the Swagger interface:
   ```bash
   http://localhost:8080/swagger-ui.html
3. Access the frontend via:
   ```bash
   http://localhost:8100


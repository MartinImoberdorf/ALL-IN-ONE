<h1 align="center">ALL IN ONE</h1>
<p align="center"> <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white" /> <img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" /> <img src="https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" /> <img src="https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white" /> <img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" /> <img src="https://img.shields.io/badge/Lombok-FFA500?style=for-the-badge&logo=java&logoColor=white" /> <img src="https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white" /> <img src="https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white" /> <img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black" /> </p>

<p align="center">
  Your personal link hub — store and access all your social media links in one place. Never forget where you saved them again!
</p>

---

## Overview

**ALL IN ONE** is a personal tool that allows you to gather all your social media and link profiles in one simple and clean interface. Stop searching across platforms or digging through notes to find your usernames — it’s all here.

---
## 🌐 Deployment Info
The application is currently deployed and available at:
👉 https://alli1.netlify.app/

Deployment Details
Backend: Deployed on Render (Free tier – may sleep when inactive)

Database: Hosted on Neon (PostgreSQL)

Frontend: Deployed on Netlify

<sub> ⚠️ Note: Since the backend is hosted on Render’s free plan, it may go to sleep after inactivity. The first request might take a few seconds. It's also hosted in the US, so you might experience slight latency depending on your location.</sub>

---

## Features

- ✅ Save all your social media links in one place
- ✅ Secure login with JWT authentication
- ✅ API documented with Swagger UI
- ✅ Data validation on backend
- ✅ Simple and clean interface with Vanilla JS frontend

---

## ⚙️ Tech Stack

### Backend (Monolith with Spring Boot)

- **Spring Web** – RESTful API endpoints
- **Spring Data JPA** – ORM with Hibernate
- **Spring Security** – Secures API routes
- **JWT (jjwt)** – Authentication using JSON Web Tokens
- **Hibernate Validator** – Entity field validations
- **Swagger / OpenAPI** – API documentation
- **PostgreSQL** – Database engine
- **Lombok** – Reduces boilerplate code

### Frontend

- Vanilla HTML, CSS & JS
- Simple and functional UI (to be improved in future versions)
- I don't use frameworks because the main focus of the project is on the backend

---

## Screenshots
<p align="center">
  <img src="https://github.com/MartinImoberdorf/ALL-IN-ONE/blob/main/Imgs/home.png" alt="Home Screenshot" width="300" height="630"/>
  <img src="https://github.com/MartinImoberdorf/ALL-IN-ONE/blob/main/Imgs/SignUp.PNG" alt="Sign Up Screenshot" width="300" height="630"/>
  <img src="https://github.com/MartinImoberdorf/ALL-IN-ONE/blob/main/Imgs/UserHome.PNG" alt="UserHome Screenshot" width="300" height="630"/>
</p>


---

## Getting Started

### Prerequisites

- Java 17+
- Gradle
- PostgreSQL

### Run the app

```bash
# Clone the repository
git clone https://github.com/MartinImoberdorf/ALL-IN-ONE.git

# Navigate to the project folder
cd ALL-IN-ONE/MyAppBackend

# Run the backend
./mvnw spring-boot:run


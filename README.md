# 🛒 Location-Based Group Buying Service
**ITWILL Training Institute: Project-Based Java Fintech Web Developer Course**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring%20Boot&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-black.svg?style=for-the-badge&logo=apache)
![JavaScript](https://img.shields.io/badge/javascript-%23F7DF1E.svg?style=for-the-badge&logo=javascript&logoColor=black)

This project is a **Location-Based Group Buying Platform** developed as a final project during the "Project-Based Java Fintech Web Developer" course at **ITWILL**. It aims to connect local neighbors to purchase products in bulk, reducing individual costs and shipping fees.

## 📌 Key Features
* **Location-Based Matching**: Browse and join group buying deals based on the user's current geographic location.
* **Group Buying Management**: Users can create, join, and track the progress of bulk purchases.
* **Fintech Integration**: Implementation of transaction handling and payment status management.
* **User Interaction**: Real-time participation tracking and community-focused deal sharing.

## 🛠 Tech Stack

### Backend
* **Java 11 / 17**
* **Spring Boot**: Main framework for application development.
* **Spring Data JPA**: For efficient database mapping and object-oriented data handling.
* **MyBatis**: Used for complex SQL optimization and legacy query management.

### Frontend
* **HTML5 / CSS3**: Responsive UI design and layout.
* **JavaScript (ES6+)**: Dynamic client-side logic and API interactions.
* **Bootstrap**: For fast and consistent UI component styling.

### Database & Tools
* **MySQL**: Relational database for storing user, product, and location data.
* **Gradle**: Dependency management and build automation.

## 📂 Project Structure
```text
src
 ├── main
 │    ├── java
 │    │    └── com.project.lion09
 │    │         ├── controller  # Web & API Controllers
 │    │         ├── service     # Business Logic & Transactions
 │    │         ├── repository  # JPA Repositories
 │    │         ├── mapper      # MyBatis XML/Interface Mappers
 │    │         └── dto/entity  # Data Transfer Objects & Entities
 │    └── resources
 │         ├── mapper           # MyBatis SQL XML files
 │         └── static/templates # CSS, JS, and HTML files

 🚀 Installation & Setup
Clone the repository

Bash

git clone [https://github.com/ejsong25/Lion09.git](https://github.com/ejsong25/Lion09.git)
Configure Database Update src/main/resources/application.yml with your database credentials.

Run the Application

Bash

./gradlew bootRun
🎓 Education Background
Institution: ITWILL Training Institute

Course: Project-Based Java Fintech Web Developer Training Course
🛒 Local-Based Group Buying Service
ITWILL Training Institute: Project-Based Java Fintech Web Developer Course

This project is a Location-Based Group Buying Platform developed as a final project during the "Project-Based Java Fintech Web Developer" course at ITWILL. It aims to connect local neighbors to purchase products in bulk, reducing costs and shipping fees.

📌 Key Features
Location-Based Matching: Browse and join group buying deals based on the user's current geographic location.

Group Buying Management: Users can create, join, and track the progress of bulk purchases.

Fintech Integration: Core logic for transaction handling and payment status management.

Admin Dashboard: Tools for managing users, reports, and active deal status.

🛠 Tech Stack
Backend
Java 11 / 17

Spring Boot: Core framework for application development.

Spring Data JPA: For efficient database mapping and object-oriented data handling.

MyBatis: For complex SQL optimization and legacy query management.

Frontend
HTML5 / CSS3: Responsive UI design.

JavaScript (ES6+): Dynamic client-side logic and API interactions.

Bootstrap: UI components and layout grid.

Database & Tools
MySQL: Relational database management.

Gradle: Dependency management and build automation.

📂 Project Architecture
Plaintext

src
 ├── main
 │    ├── java
 │    │    └── com.project.lion09
 │    │         ├── controller  # Web & API Controllers
 │    │         ├── service     # Business logic & Transactions
 │    │         ├── repository  # JPA Repositories
 │    │         ├── mapper      # MyBatis XML/Interface mappers
 │    │         └── dto/entity  # Data Transfer Objects & Entities
 │    └── resources
 │         ├── mapper           # MyBatis SQL XML files
 │         └── static/templates # CSS, JS, and HTML files
🚀 Installation & Setup
Clone the repository

Bash

git clone https://github.com/ejsong25/Lion09.git
Configure Database Update src/main/resources/application.yml with your database credentials.

Run the Application

Bash

./gradlew bootRun
🎓 Education Background
Institution: ITWILL Training Institute

Course: Project-Based Java Fintech Web Developer Training Course

Duration: 2023-05-03 ~ 2023-10-31
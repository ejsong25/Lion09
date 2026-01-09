# 🛒 Local-Based Group Buying Service
**ITWILL Training Institute: Project-Based Java Fintech Web Developer Course**

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=Spring%20Boot&logoColor=white)
![MyBatis](https://img.shields.io/badge/MyBatis-black.svg?style=for-the-badge&logo=apache)
![JavaScript](https://img.shields.io/badge/javascript-%23F7DF1E.svg?style=for-the-badge&logo=javascript&logoColor=black)

This project is a **Location-Based Group Buying Platform** developed during the "Project-Based Java Fintech Web Developer" course at **ITWILL**. It features a robust architecture integrating community boards, real-time chatting, and order/payment systems.

## 📌 Key Modules & Features
* **Board & QA**: Community management for sharing local deal information and a dedicated Q&A system for user support.
* **Chat System**: Real-time communication module to facilitate group buy coordination between neighbors.
* **Order & Pay**: Fintech-focused order processing and payment status management system.
* **Member & Login**: Secure user authentication with session-based login management (`SessionConst`, `InterceptorConfig`).
* **MyPage**: Personalized dashboard for users to manage their profiles, orders, and activities.

## 🛠 Tech Stack
### Backend
* **Java 11 / 17**
* **Spring Boot**: Application core and MVC framework.
* **Spring Data JPA & MyBatis**: Hybrid ORM approach for flexible and optimized data access.
* **Spring Security / Interceptor**: Secure session and access control management.

### Frontend
* **JavaScript (ES6+)**: Frontend logic and asynchronous API handling.
* **Thymeleaf / HTML5**: Server-side rendering for dynamic web content.
* **CSS3 & Bootstrap**: Responsive and user-friendly UI design.

### Infrastructure
* **MySQL**: Relational database for persistent data storage.
* **Gradle**: Project automation and dependency management.

## 📂 Project Structure
```text
src/main/java/com/lion09
 ├── board        # Community post management
 ├── chat         # Real-time messaging logic
 ├── login        # Authentication & Session handling
 ├── member       # User profile & registration
 ├── mypage       # User dashboard
 ├── order        # Group buy order processing
 ├── pay          # Fintech & Payment integration
 ├── qaboard      # Customer support system
 ├── InterceptorConfig.java # Security Interceptor
 └── Lion09Application.java # Application Entry Point
# 🛒 Local-Based Group Buying Service (Lion09)
**ITWILL Training Institute: Project-Based Java Fintech Web Developer Course**

[![Java](https://img.shields.io/badge/java-11%2F17-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.15-6DB33F?style=for-the-badge&logo=Spring%20Boot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MyBatis](https://img.shields.io/badge/MyBatis-3.0.0-black.svg?style=for-the-badge&logo=apache)](https://mybatis.org/mybatis-3/)
[![JPA](https://img.shields.io/badge/JPA-Hibernate-blue.svg?style=for-the-badge&logo=hibernate)](https://spring.io/projects/spring-data-jpa)
[![AWS S3](https://img.shields.io/badge/AWS%20S3-Cloud%20Storage-FF9900?style=for-the-badge&logo=amazons3&logoColor=white)](https://aws.amazon.com/s3/)

## 📝 Overview
This project is a **Location-Based Group Buying Platform** developed during the "Project-Based Java Fintech Web Developer" course at **ITWILL**. It facilitates community-driven commerce by allowing neighbors to coordinate group purchases, reducing costs and fostering local interaction.

### 🌟 Key Features
*   **Board & QA**: Community boards for sharing local deal information and a structured Q&A system for user support.
*   **Real-time Chat**: STOMP-based WebSocket communication for seamless coordination between group buy participants.
*   **Fintech Integration (LionPay)**: Simulated payment system with order processing and status management.
*   **Location-Based Services**: Integrated geocoding to help users find deals in their vicinity.
*   **Member Management**: Secure authentication with session-based login and personalized user dashboards (MyPage).
*   **Cloud Integration**: Robust file handling using **Amazon S3** for user profile images and product photos.

## 🛠 Tech Stack
### Backend
*   **Language**: Java 11 / 17
*   **Framework**: Spring Boot 2.7.15
*   **Persistence**: 
    *   **Spring Data JPA**: For domain model management and complex relationships.
    *   **MyBatis**: For optimized SQL queries and complex data mapping.
*   **Communication**: WebSocket with **STOMP** for real-time messaging.
*   **Security**: Spring Security & Custom Interceptor for session-based access control.
*   **Mapping**: MapStruct for clean DTO-to-Entity conversions.

### Frontend
*   **Rendering**: Thymeleaf template engine.
*   **Styling**: CSS3, Bootstrap for responsive design.
*   **Logic**: JavaScript (ES6+), SockJS, Stomp.js.

### Infrastructure & Tools
*   **Database**: H2 Database (File-based storage for development).
*   **Cloud Storage**: Amazon S3 (ap-northeast-2).
*   **Build Tool**: Gradle.

## 📂 Project Structure
```text
src/main/java/com/lion09
 ├── board          # Community post management logic
 ├── chat           # STOMP-based WebSocket chatting modules
 ├── login          # User authentication & session handling
 ├── member         # Profile management & user registration
 ├── mypage         # User-specific dashboards and activity logs
 ├── order          # Core group buy transaction processing
 ├── pay            # LionPay fintech module & payment simulation
 ├── qaboard        # Customer support & inquiry system
 ├── InterceptorConfig.java # Security & Session Interceptors
 └── Lion09Application.java # Spring Boot main entry point
```

## 🚀 Getting Started
### Prerequisites
*   JDK 11 or 17
*   Gradle 7.x
*   (Optional) AWS Access Key & Secret (Configured in `application.properties`)

### Setup & Run
1.  **Clone the repository:**
    ```bash
    git clone https://github.com/ejsong25/Lion09.git
    cd Lion09/Lion09
    ```
2.  **Configure Environment:**
    Update `src/main/resources/application.properties` with your database credentials and AWS S3 keys if necessary.
3.  **Run with Gradle:**
    ```bash
    ./gradlew bootRun
    ```
4.  **Access the application:**
    Open [http://localhost:8080](http://localhost:8080) in your browser.
    *   **H2 Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:tcp://localhost/~/lion`)

---
© 2026 ITWILL Lion09 Project Team.
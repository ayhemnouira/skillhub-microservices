🔐 SkillHub Auth Service

Professional authentication microservice built with Spring Boot 3, MongoDB, and JWT

Afficher l'image Afficher l'image Afficher l'image Afficher l'image Afficher l'image 📋 Overview SkillHub Auth Service is a production-ready authentication microservice that provides secure user authentication, email verification, password management, and JWT-based authorization. Built as part of the SkillHub platform - a professional learning and job matching system. ✨ Key Features

🔐 JWT Authentication - Stateless token-based authentication with HS512 algorithm 📧 Email Verification - Secure 6-digit OTP verification with 10-minute expiry 🔑 Password Management - BCrypt hashing (strength 12) with forgot/reset functionality 🔄 Refresh Tokens - Long-lived tokens (7 days) for seamless user experience 🛡️ Security - Account lockout, token rotation, CORS protection 🐳 Docker Ready - Containerized with Docker Compose 📊 Health Monitoring - Spring Boot Actuator integration 🔍 Service Discovery - Eureka client for microservices architecture

🏗️ Architecture ┌─────────────────────────────────────────────┐ │ API Gateway (Port 8080) │ └────────────────┬────────────────────────────┘ │ ▼ ┌─────────────────────────────────────────────┐ │ Auth Service (Port 8081) │ │ ┌──────────────────────────────────────┐ │ │ │ JWT Security Layer │ │ │ │ • Token Generation │ │ │ │ • Token Validation │ │ │ │ • User Authentication │ │ │ └──────────────────────────────────────┘ │ │ ┌──────────────────────────────────────┐ │ │ │ Business Logic │ │ │ │ • User Registration │ │ │ │ • Email Verification │ │ │ │ • Password Management │ │ │ └──────────────────────────────────────┘ │ │ ┌──────────────────────────────────────┐ │ │ │ MongoDB Database │ │ │ │ • users │ │ │ │ • verification_tokens │ │ │ │ • refresh_tokens │ │ │ └──────────────────────────────────────┘ │ └─────────────────────────────────────────────┘

🚀 Quick Start Prerequisites

Java 17 or higher Maven 3.6+ Docker Desktop MongoDB (via Docker or local)

Installation

Clone the repository

bashgit clone https://github.com/yourusername/skillhub-auth-service.git cd skillhub-auth-service

Configure environment variables

bash# Create .env file or set environment variables export MONGODB_URI=mongodb://localhost:27017/skillhub_auth export JWT_SECRET=your-512-bit-secret-key export MAIL_USERNAME=your-email@gmail.com export MAIL_PASSWORD=your-app-password

Start MongoDB with Docker

bashdocker-compose up -d mongodb

Build and run

bashmvn clean package java -jar target/auth-service-1.0.0.jar Or run with Docker: bashdocker-compose up -d

Verify service is running

bashcurl http://localhost:8081/actuator/health

📡 API Endpoints Authentication Endpoints MethodEndpointDescriptionAuth RequiredPOST/api/auth/registerRegister new userNoPOST/api/auth/verify-emailVerify email with OTPNoPOST/api/auth/loginLogin and get tokensNoPOST/api/auth/forgot-passwordRequest password resetNoPOST/api/auth/reset-passwordReset password with tokenNoPOST/api/auth/refresh-tokenRefresh access tokenNoPOST/api/auth/logoutLogout userYesGET/api/auth/validate-tokenValidate JWT tokenYes Example Requests Register User bashPOST /api/auth/register Content-Type: application/json

{ "email": "user@example.com", "password": "SecurePass@123", "role": "USER" } Login bashPOST /api/auth/login Content-Type: application/json

{ "email": "user@example.com", "password": "SecurePass@123" } Response: json{ "accessToken": "eyJhbGciOiJIUzUxMiJ9...", "refreshToken": "550e8400-e29b-41d4-a716...", "tokenType": "Bearer", "userId": "507f1f77bcf86cd799439011", "email": "user@example.com", "roles": ["USER"], "status": "ACTIVE" } Verify Email bashPOST /api/auth/verify-email Content-Type: application/json

{ "email": "user@example.com", "otp": "123456" }

🔐 Security Features Password Security

BCrypt Hashing: Strength 12 rounds (OWASP recommended) Password Policy: Minimum 8 characters, uppercase, lowercase, digit, special character Salt Generation: Automatic per-password salt

JWT Tokens

Algorithm: HS512 (512-bit HMAC) Access Token: 24 hours expiry Refresh Token: 7 days expiry, stored in database Token Rotation: Refresh tokens rotated on use

Account Security

Email Verification: Required before first login Account Lockout: 5 failed login attempts OTP Expiry: 6-digit codes expire in 10 minutes Password Reset: Secure UUID tokens with 1-hour expiry

🛠️ Technology Stack Backend

Java 17 - Modern Java features Spring Boot 3.2.0 - Application framework Spring Security 6 - Authentication & authorization Spring Data MongoDB - Database access JJWT 0.12.3 - JWT implementation

Database

MongoDB 7.0 - NoSQL document database

DevOps

Docker - Containerization Docker Compose - Multi-container orchestration Spring Cloud Eureka - Service discovery Spring Boot Actuator - Health monitoring

Email

Spring Mail - Email integration Gmail SMTP - Email delivery

📂 Project Structure auth-service/ ├── src/main/java/com/skillhub/auth/ │ ├── config/ # Configuration classes │ ├── controller/ # REST controllers │ ├── dto/ # Data transfer objects │ ├── entity/ # MongoDB entities │ ├── exception/ # Exception handling │ ├── repository/ # Database repositories │ ├── security/ # Security components │ ├── service/ # Business logic │ └── util/ # Utility classes ├── src/main/resources/ │ ├── application.yml # Main configuration │ └── application-*.yml # Environment configs ├── docker-compose.yml # Docker configuration ├── Dockerfile # Container build file └── pom.xml # Maven dependencies

⚙️ Configuration Environment Variables VariableDescriptionRequiredDefaultMONGODB_URIMongoDB connection stringYesmongodb://localhost:27017/skillhub_authJWT_SECRETSecret key for JWT signing (512-bit)Yes-MAIL_USERNAMEEmail account usernameYes-MAIL_PASSWORDEmail account password/app passwordYes-SPRING_PROFILES_ACTIVEActive Spring profileNodev Gmail SMTP Setup

Enable 2-Factor Authentication Generate App Password: https://myaccount.google.com/apppasswords Use the 16-character password in configuration

🧪 Testing Run Tests bashmvn test Manual Testing with cURL Health Check: bashcurl http://localhost:8081/actuator/health Register User: bashcurl -X POST http://localhost:8081/api/auth/register
-H "Content-Type: application/json"
-d '{"email":"test@example.com","password":"Test@1234"}' Login: bashcurl -X POST http://localhost:8081/api/auth/login
-H "Content-Type: application/json"
-d '{"email":"test@example.com","password":"Test@1234"}'

📊 Monitoring Actuator Endpoints

Health: /actuator/health Info: /actuator/info Metrics: /actuator/metrics

Health Check Response json{ "status": "UP", "components": { "mongo": { "status": "UP", "details": { "version": "7.0.0" } }, "ping": { "status": "UP" } } }

🐳 Docker Deployment Build Docker Image bashmvn clean package -DskipTests docker build -t skillhub-auth-service . Run with Docker Compose bashdocker-compose up -d View Logs bashdocker logs -f skillhub-auth-service Stop Services bashdocker-compose down

🔄 Development Workflow

Create Feature Branch

bashgit checkout -b feature/new-feature

Make Changes and Test

bashmvn test mvn spring-boot:run

Commit Changes

bashgit add . git commit -m "feat: add new feature"

Push to GitHub

bashgit push origin feature/new-feature

📝 Future Enhancements

OAuth 2.0 integration (Google, GitHub) Two-factor authentication (TOTP) Rate limiting with Redis API documentation with Swagger Comprehensive unit and integration tests Grafana dashboard for monitoring CI/CD pipeline with GitHub Actions Kubernetes deployment manifests

🤝 Contributing Contributions are welcome! Please follow these steps:

Fork the repository Create a feature branch (git checkout -b feature/AmazingFeature) Commit your changes (git commit -m 'feat: add AmazingFeature') Push to the branch (git push origin feature/AmazingFeature) Open a Pull Request

📄 License This project is licensed under the MIT License - see the LICENSE file for details.

👤 Author Your Name

GitHub: @yourusername LinkedIn: Your Name Email: your.email@example.com

🙏 Acknowledgments

Spring Boot team for excellent documentation MongoDB for flexible NoSQL database JWT.io for JWT debugging tools The open-source community

📞 Support For support, email your.email@example.com or open an issue in the GitHub repository.

⭐ Star this repository if you find it helpful! Made with ❤️ for the SkillHub Platform

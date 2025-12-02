# 🔐 SkillHub - Professional Learning & Job Matching Platform

> Enterprise-grade microservices platform built with Spring Boot 4, Spring Cloud, MongoDB, and React

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.0.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Cloud](https://img.shields.io/badge/Spring%20Cloud-2025.0.0-blue.svg)](https://spring.io/projects/spring-cloud)
[![MongoDB](https://img.shields.io/badge/MongoDB-8.0-green.svg)](https://www.mongodb.com/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📋 Project Overview

SkillHub is a modern microservices-based platform that connects job seekers with professional courses, certifications, and job opportunities. The system demonstrates production-ready patterns including service discovery, API gateway, distributed authentication, and inter-service communication.

### 🎯 Business Value

- **For Job Seekers**: Discover courses, build skills, and find matching job opportunities
- **For Recruiters**: Post jobs, review applications, and find qualified candidates
- **For Educators**: Publish courses and track student progress

---

## 🏗️ System Architecture

```
┌──────────────────────────────────────────────────────────┐
│              REACT.JS FRONTEND (Coming Soon)             │
│         (Progressive Web App, Responsive Design)          │
└────────────────────┬─────────────────────────────────────┘
                     │
                     ▼
┌──────────────────────────────────────────────────────────┐
│            API GATEWAY (Port 8080) ✅                    │
│  ✅ JWT Validation  ✅ Rate Limiting  ✅ Circuit Breaker │
│  ✅ Request Routing ✅ CORS          ✅ Load Balancing   │
└────────────────────┬─────────────────────────────────────┘
                     │
      ┌──────────────┼──────────────┬──────────────┐
      │              │              │              │
      ▼              ▼              ▼              ▼
┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐
│   Auth   │  │  Course  │  │   Job    │  │  User    │
│ Service  │  │ Service  │  │ Service  │  │ Profile  │
│  ✅ 8081 │  │  📋 8082 │  │  📋 8083 │  │ Service  │
│ MongoDB  │  │ MongoDB  │  │ MongoDB  │  │  📋 8084 │
└──────────┘  └──────────┘  └──────────┘  │ MongoDB  │
      │              │              │      └──────────┘
      │              ▼              ▼              │
      │        ┌──────────┐  ┌──────────┐        │
      │        │Enrollment│  │Application│       │
      │        │ Service  │  │  Service │       │
      │        │  📋 8085 │  │  📋 8086 │       │
      │        │ MongoDB  │  │ MongoDB  │       │
      │        └──────────┘  └──────────┘       │
      │              │              │              │
      └──────────────┼──────────────┼──────────────┘
                     │              │
                     ▼              ▼
              ┌─────────────────────────┐
              │  Notification Service   │
              │        📋 8087          │
              │   Email + WebSocket     │
              └─────────────────────────┘
                     │
                     ▼
          ┌──────────────────────┐
          │  Service Registry    │
          │  ✅ Eureka - 8761    │
          └──────────────────────┘
```

**Legend:**
- ✅ Complete & Tested
- 🚧 In Progress
- 📋 Planned

---

## 🚀 Microservices Overview

| Service | Port | Database | Status | Documentation |
|---------|------|----------|--------|---------------|
| **Service Registry** | 8761 | - | ✅ Complete | [Eureka Dashboard](http://localhost:8761) |
| **API Gateway** | 8080 | - | ✅ Complete | Routes all client requests |
| **Auth Service** | 8081 | MongoDB | ✅ Complete | [View Docs](./auth-service/README.md) |
| **User Profile Service** | 8084 | MongoDB | 📋 Planned | Skills, experience, resume |
| **Course Service** | 8082 | MongoDB | 📋 Planned | Course catalog & reviews |
| **Enrollment Service** | 8085 | MongoDB | 📋 Planned | Course enrollments & certificates |
| **Job Service** | 8083 | MongoDB | 📋 Planned | Job postings & search |
| **Application Service** | 8086 | MongoDB | 📋 Planned | Job applications tracking |
| **Notification Service** | 8087 | - | 📋 Planned | Email & real-time notifications |

---

## ✨ Key Features Implemented

### ✅ Service Registry (Eureka Server)
- Service discovery and registration
- Health monitoring dashboard
- Load balancer integration
- Automatic service de-registration

### ✅ API Gateway (Spring Cloud Gateway)
- Centralized routing to all microservices
- JWT token validation
- Rate limiting (Resilience4j)
- Circuit breaker pattern
- CORS configuration
- Request/Response logging

### ✅ Auth Service
- **User Registration** with email verification (6-digit OTP)
- **JWT Authentication** (Access + Refresh tokens)
- **Password Management** (BCrypt hashing, forgot/reset flow)
- **Role-Based Access Control** (USER, RECRUITER, ADMIN)
- **Account Security** (lockout after failed attempts)
- **Token Refresh** mechanism
- **Logout** with token blacklist

---

## 🛠️ Technology Stack

### Backend
- **Java 21** - Modern Java features (Records, Virtual Threads, Pattern Matching)
- **Spring Boot 4.0.0** - Application framework
- **Spring Cloud Gateway** - API Gateway
- **Spring Cloud Netflix Eureka** - Service discovery
- **Spring Security 6** - Authentication & authorization
- **Spring Data MongoDB** - Database access
- **JJWT 0.12.3** - JWT token implementation
- **Resilience4j** - Circuit breaker & rate limiting
- **Spring Mail** - Email integration

### Database
- **MongoDB 8.0** - NoSQL document database (one database per service)

### DevOps & Tools
- **Maven** - Dependency management
- **Docker** - Containerization (optional)
- **Postman** - API testing
- **Git** - Version control

### Frontend (Planned)
- **React 18** with Vite
- **Tailwind CSS** - Styling
- **Axios** - HTTP client
- **React Router** - Navigation

---

## 🚀 Getting Started

### Prerequisites

Ensure you have the following installed:
- **Java 21** or higher ([Download](https://www.oracle.com/java/technologies/downloads/))
- **Maven 3.9+** ([Download](https://maven.apache.org/download.cgi))
- **MongoDB 8.0** ([Download](https://www.mongodb.com/try/download/community) or use Docker)
- **Git** ([Download](https://git-scm.com/downloads))
- **Postman** (optional, for API testing)

### Installation Steps

1. **Clone the repository**
```bash
git clone https://github.com/ayhemnouira/skillhub-microservices.git
cd skillhub-microservices
```

2. **Start MongoDB**

Using Docker:
```bash
docker run -d -p 27017:27017 --name mongodb mongo:8.0
```

Or start your local MongoDB instance.

3. **Start Service Registry (Eureka)**
```bash
cd service-registry
mvn clean install
mvn spring-boot:run
```
Access Eureka Dashboard at: http://localhost:8761

4. **Start API Gateway**
```bash
cd api-gateway
mvn clean install
mvn spring-boot:run
```

5. **Start Auth Service**
```bash
cd auth-service

# Create .env file (copy from .env.example)
cp .env.example .env

# Edit .env and add your credentials:
# MONGODB_URI=mongodb://localhost:27017/skillhub_auth
# JWT_SECRET=your-512-bit-secret-key
# MAIL_USERNAME=your-email@gmail.com
# MAIL_PASSWORD=your-gmail-app-password

mvn clean install
mvn spring-boot:run
```

6. **Verify all services are running**

Check Eureka Dashboard: http://localhost:8761

You should see:
- ✅ API-GATEWAY
- ✅ AUTH-SERVICE

---

## 🔐 Environment Variables

Each service requires specific environment variables. Create a `.env` file in each service directory:

### Auth Service (`auth-service/.env`)
```env
MONGODB_URI=mongodb://localhost:27017/skillhub_auth
JWT_SECRET=your-very-long-secret-key-at-least-512-bits-for-hs512-algorithm
JWT_ACCESS_EXPIRATION=86400000
JWT_REFRESH_EXPIRATION=604800000
MAIL_USERNAME=your-email@gmail.com
MAIL_PASSWORD=your-gmail-app-password
SPRING_PROFILES_ACTIVE=dev
```

### Gmail SMTP Setup
1. Enable 2-Factor Authentication on your Gmail account
2. Generate an App Password: https://myaccount.google.com/apppasswords
3. Use the 16-character password in `MAIL_PASSWORD`

---

## 📡 API Endpoints

All requests go through the API Gateway at `http://localhost:8080`

### Auth Service Endpoints

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/auth/register` | Register new user | No |
| POST | `/api/auth/verify-email` | Verify email with OTP | No |
| POST | `/api/auth/login` | Login and get JWT tokens | No |
| POST | `/api/auth/refresh-token` | Refresh access token | No |
| POST | `/api/auth/forgot-password` | Request password reset | No |
| POST | `/api/auth/reset-password` | Reset password | No |
| POST | `/api/auth/logout` | Logout and invalidate token | Yes |
| GET | `/api/auth/validate-token` | Validate JWT token | Yes |

### Example: Register a User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass@123",
    "role": "USER"
  }'
```

### Example: Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "SecurePass@123"
  }'
```

**Response:**
```json
{
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "550e8400-e29b-41d4-a716...",
  "tokenType": "Bearer",
  "userId": "507f1f77bcf86cd799439011",
  "email": "user@example.com",
  "roles": ["USER"],
  "status": "ACTIVE"
}
```

---

## 🧪 Testing

### Health Checks

**Eureka Dashboard:**
```bash
curl http://localhost:8761
```

**API Gateway Health:**
```bash
curl http://localhost:8080/actuator/health
```

**Auth Service Health:**
```bash
curl http://localhost:8081/actuator/health
```

### Postman Collection

Import the Postman collection for complete API testing: [Coming Soon]

---

## 📂 Project Structure

```
skillhub-microservices/
├── README.md                    # This file
├── .gitignore                   # Git ignore rules
├── docker-compose.yml           # Docker setup (optional)
│
├── service-registry/            # Eureka Server (Port 8761)
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── api-gateway/                 # Spring Cloud Gateway (Port 8080)
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── auth-service/                # Authentication Service (Port 8081)
│   ├── src/
│   ├── pom.xml
│   ├── .gitignore
│   ├── .env.example
│   └── README.md                # Detailed auth docs
│
├── user-profile-service/        # 📋 Planned (Port 8084)
├── course-service/              # 📋 Planned (Port 8082)
├── enrollment-service/          # 📋 Planned (Port 8085)
├── job-service/                 # 📋 Planned (Port 8083)
├── application-service/         # 📋 Planned (Port 8086)
└── notification-service/        # 📋 Planned (Port 8087)
```

---

## 🔐 Security Features

- ✅ **JWT Authentication** - HS512 algorithm with access & refresh tokens
- ✅ **Password Encryption** - BCrypt with strength 12
- ✅ **Email Verification** - 6-digit OTP with 10-minute expiry
- ✅ **Account Lockout** - After 5 failed login attempts
- ✅ **Token Refresh** - Seamless token renewal
- ✅ **CORS Protection** - Configured in API Gateway
- ✅ **Rate Limiting** - Request throttling per endpoint

---

## 🚀 Roadmap

### Phase 1: Foundation ✅ (Completed)
- [x] Service Registry (Eureka)
- [x] API Gateway with security
- [x] Auth Service with JWT

### Phase 2: Core Services 📋 (In Progress)
- [ ] User Profile Service
- [ ] Course Service with reviews
- [ ] Enrollment Service with Saga pattern

### Phase 3: Job Matching 📋 (Planned)
- [ ] Job Service
- [ ] Application Service with workflow
- [ ] Recommendation algorithm

### Phase 4: Notifications & Advanced Features 📋 (Planned)
- [ ] Notification Service (Email + WebSocket)
- [ ] Circuit breaker patterns
- [ ] Distributed tracing
- [ ] Caching with Redis

### Phase 5: Frontend & DevOps 📋 (Planned)
- [ ] React.js frontend
- [ ] Docker Compose setup
- [ ] CI/CD pipeline
- [ ] Kubernetes manifests

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'feat: add AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### Commit Message Convention
- `feat:` - New feature
- `fix:` - Bug fix
- `docs:` - Documentation changes
- `refactor:` - Code refactoring
- `test:` - Adding tests
- `chore:` - Maintenance tasks

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👤 Author

**Ayhem Nouira**
- GitHub: [@ayhemnouira](https://github.com/ayhemnouira)
- LinkedIn: [linkedin.com/in/ayhemnouira](https://www.linkedin.com/in/ayhemnouira/)
- Email: ayhemnouira9@gmail.com

---

## 🙏 Acknowledgments

- Spring Boot & Spring Cloud teams for excellent frameworks
- MongoDB for flexible NoSQL database
- The open-source community for inspiration

---

## 📞 Support

For questions or support:
- 📧 Email: ayhemnouira9@gmail.com
- 🐛 Issues: [GitHub Issues](https://github.com/ayhemnouira/skillhub-microservices/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/ayhemnouira/skillhub-microservices/discussions)

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ using Spring Boot & Microservices Architecture

[Report Bug](https://github.com/ayhemnouira/skillhub-microservices/issues) · [Request Feature](https://github.com/ayhemnouira/skillhub-microservices/issues)

</div>

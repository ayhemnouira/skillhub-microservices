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
│ MongoDB  │  │ MongoDB  │  │ MongoDB  │  │  ✅ 8084 │
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
| **User Profile Service** | 8084 | MongoDB | ✅ Complete | [View Docs](./user-profile-service/README.md) |
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

### ✅ User Profile Service ⭐ NEW!
- **Profile Management** (CRUD operations)
- **Skills Management** (add/remove with case-insensitive deduplication)
- **Experience Tracking** (full work history with CRUD)
- **Education Management** (academic background)
- **Profile Completion Algorithm** (0-100% gamification score like LinkedIn!)
- **Resume Upload** capability
- **Recruiter Search** (find candidates by skills/location)
- **MongoDB Embedded Documents** (optimized data structure)
- **Global Exception Handling** (consistent error responses)
- **Bean Validation** (input validation at DTO layer)

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
cp .env.example .env
# Edit .env with your credentials
mvn clean install
mvn spring-boot:run
```

6. **Start User Profile Service** 
```bash
cd user-profile-service
mvn clean install
mvn spring-boot:run
```

7. **Verify all services are running**

Check Eureka Dashboard: http://localhost:8761

You should see:
- ✅ API-GATEWAY
- ✅ AUTH-SERVICE
- ✅ USER-PROFILE-SERVICE

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

### User Profile Service (`user-profile-service/.env`)
```env
MONGODB_URI=mongodb://localhost:27017/skillhub_profiles
SPRING_PROFILES_ACTIVE=dev
```

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

### User Profile Service Endpoints ⭐ NEW!

| Method | Endpoint | Description | Auth Required |
|--------|----------|-------------|---------------|
| POST | `/api/profiles` | Create profile | Yes |
| GET | `/api/profiles/user/{userId}` | Get profile by userId | Yes |
| GET | `/api/profiles/{id}` | Get profile by profileId | Yes |
| PUT | `/api/profiles/{id}` | Update profile | Yes |
| DELETE | `/api/profiles/{id}` | Delete profile | Yes |
| POST | `/api/profiles/{id}/skills` | Add skill | Yes |
| DELETE | `/api/profiles/{id}/skills/{skill}` | Remove skill | Yes |
| POST | `/api/profiles/{id}/experience` | Add experience | Yes |
| PUT | `/api/profiles/{id}/experience/{expId}` | Update experience | Yes |
| DELETE | `/api/profiles/{id}/experience/{expId}` | Delete experience | Yes |
| POST | `/api/profiles/{id}/education` | Add education | Yes |
| PUT | `/api/profiles/{id}/education/{eduId}` | Update education | Yes |
| DELETE | `/api/profiles/{id}/education/{eduId}` | Delete education | Yes |
| GET | `/api/profiles/{id}/completion` | Get completion % | Yes |
| GET | `/api/profiles/search/skills?skills=Java,Spring` | Search by skills | Yes |
| GET | `/api/profiles/search/location?location=Tunis` | Search by location | Yes |

### Example: Create Profile
```bash
curl -X POST http://localhost:8080/api/profiles \
  -H "Content-Type: application/json" \
  -H "X-User-Id: 673f4e8a9b1c2d3e4f5a6b7c" \
  -d '{
    "firstName": "Ahmed",
    "lastName": "Ben Salem",
    "title": "Junior Java Developer",
    "bio": "Passionate about microservices",
    "phoneNumber": "+216 12 345 678",
    "location": "Tunis, Tunisia"
  }'
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

**User Profile Service Health:**
```bash
curl http://localhost:8084/actuator/health
```

---

## 📂 Project Structure

```
skillhub-microservices/
├── README.md                    # This file
├── .gitignore                   # Git ignore rules
├── docker-compose.yml           # Docker setup (optional)
│
├── service-registry/            # Eureka Server (Port 8761) ✅
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── api-gateway/                 # Spring Cloud Gateway (Port 8080) ✅
│   ├── src/
│   ├── pom.xml
│   └── README.md
│
├── auth-service/                # Authentication Service (Port 8081) ✅
│   ├── src/
│   ├── pom.xml
│   ├── .gitignore
│   ├── .env.example
│   └── README.md
│
├── user-profile-service/        # User Profile Service (Port 8084) ✅ NEW!
│   ├── src/
│   │   └── main/
│   │       ├── java/com/skillhub/profile/
│   │       │   ├── UserProfileServiceApplication.java
│   │       │   ├── controller/
│   │       │   │   └── ProfileController.java
│   │       │   ├── service/
│   │       │   │   ├── ProfileService.java
│   │       │   │   └── ProfileServiceImpl.java
│   │       │   ├── repository/
│   │       │   │   └── ProfileRepository.java
│   │       │   ├── model/
│   │       │   │   ├── UserProfile.java
│   │       │   │   ├── Experience.java
│   │       │   │   └── Education.java
│   │       │   ├── dto/
│   │       │   │   ├── ProfileRequest.java
│   │       │   │   ├── ExperienceRequest.java
│   │       │   │   ├── EducationRequest.java
│   │       │   │   └── ErrorResponse.java
│   │       │   └── exception/
│   │       │       ├── ProfileNotFoundException.java
│   │       │       └── GlobalExceptionHandler.java
│   │       └── resources/
│   │           └── application.yml
│   ├── pom.xml
│   └── README.md
│
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
- ✅ **Input Validation** - Bean Validation at DTO layer
- ✅ **Global Exception Handling** - Consistent error responses

---

## 🚀 Roadmap

### Phase 1: Foundation ✅ (Completed)
- [x] Service Registry (Eureka)
- [x] API Gateway with security
- [x] Auth Service with JWT

### Phase 2: Core Services 🚧 (In Progress - 50% Complete)
- [x] User Profile Service ✅ **JUST COMPLETED!**
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

## 💡 Key Architectural Decisions

### Why MongoDB?
- Document database perfect for nested data (Experience, Education)
- No JOINs needed (embedded documents)
- Flexible schema for evolving requirements
- Horizontal scaling capability

### Why Microservices?
- **Independent Scaling**: Profile service can scale separately from Auth
- **Technology Flexibility**: Each service can use different tech stack
- **Team Independence**: Different teams work on different services
- **Fault Isolation**: If one service fails, others continue working

### Why Embedded Documents (Experience/Education)?
- Always queried together with profile
- Eliminates expensive JOINs
- Atomic updates (update profile + experience in one operation)
- Better performance (one database query instead of three)

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

---

## 🙏 Acknowledgments

- Spring Boot & Spring Cloud teams for excellent frameworks
- MongoDB for flexible NoSQL database
- The open-source community for inspiration

---

## 📞 Support

For questions or support:
- 🐛 Issues: [GitHub Issues](https://github.com/ayhemnouira/skillhub-microservices/issues)
- 💬 Discussions: [GitHub Discussions](https://github.com/ayhemnouira/skillhub-microservices/discussions)

---

<div align="center">

**⭐ Star this repository if you find it helpful!**

Made with ❤️ using Spring Boot & Microservices Architecture

[Report Bug](https://github.com/ayhemnouira/skillhub-microservices/issues) · [Request Feature](https://github.com/ayhemnouira/skillhub-microservices/issues)

</div>

# ✈️ Airline Reservation System

> A full-stack airline reservation platform for flight search, seat selection, passenger management, booking, payment, authentication, and administration.

---

## 📌 Table of Contents

- [About the Project](#-about-the-project)
- [Key Features](#-key-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Application Modules](#-application-modules)
- [API Modules](#-api-modules)
- [Database Architecture](#-database-architecture)
- [Booking Workflow](#-booking-workflow)
- [Security](#-security)
- [Local Development](#-local-development)
- [Configuration](#-configuration)
- [Testing](#-testing)
- [Future Enhancements](#-future-enhancements)
- [Contributing](#-contributing)
- [Show Your Support](#-show-your-support)
- [Let's Connect](#-lets-connect)
- [Author](#-author)
- [License](#-license)

---

# 🚀 About the Project

The **Airline Reservation System** is a full-stack web application designed to simulate a real-world airline booking platform.

The application provides a complete reservation workflow:

**Flight Search → Flight Selection → Seat Selection → Passenger Details → Payment → Booking Confirmation → Booking Management**

The project is built using Java, Spring Boot, React, MySQL, MongoDB, Redis, Apache Kafka, Spring Security, JWT, and modern testing/API tools.

The application is designed with a modular architecture so that users and administrators can manage flights, seats, passengers, bookings, payments, and other airline operations through dedicated modules.

---

# ✨ Key Features

## 👤 User Features

- User registration
- User login
- JWT-based authentication
- Secure password handling
- Role-based access control
- Flight search
- Flight filtering
- Flight details
- Seat availability
- Seat selection
- Passenger management
- Mock payment processing
- Booking creation
- PNR generation
- Booking confirmation
- Booking history
- Booking details
- Booking cancellation
- User profile management

## 🛠️ Admin Features

- Secure admin authentication
- Admin dashboard
- User management
- Flight management
- Airport management
- Aircraft management
- Seat management
- Booking management
- Payment monitoring
- System activity monitoring
- Audit information

## ⚡ Backend Features

- RESTful APIs
- Spring Boot
- Spring MVC
- Spring Data JPA
- Hibernate ORM
- DTO-based architecture
- Jakarta Bean Validation
- Global exception handling
- Transaction management
- JWT authentication
- Role-based authorization
- User ownership authorization
- Redis caching
- Temporary seat locking
- Apache Kafka event processing
- MongoDB audit/activity storage

## 🎨 Frontend Features

- Responsive React UI
- React Router
- Axios API integration
- Authentication flow
- Protected routes
- User dashboard
- Flight search interface
- Flight results
- Flight details
- Interactive seat selection
- Passenger information form
- Payment interface
- Booking confirmation
- My Bookings
- Booking details
- User profile
- Admin dashboard
- Admin management screens

---

# 🧰 Tech Stack

## ☕ Backend Technologies

| Technology | Purpose |
|---|---|
| ![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white) | Core programming language |
| ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white) | Backend framework |
| ![Spring MVC](https://img.shields.io/badge/Spring%20MVC-REST-6DB33F?style=for-the-badge&logo=spring&logoColor=white) | REST API development |
| ![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white) | Authentication & authorization |
| ![Hibernate](https://img.shields.io/badge/Hibernate-ORM-59666C?style=for-the-badge&logo=hibernate&logoColor=white) | ORM |
| ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white) | Build and dependency management |

## 🎨 Frontend Technologies

| Technology | Purpose |
|---|---|
| ![React](https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black) | Frontend framework |
| ![JavaScript](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black) | Frontend programming |
| ![HTML5](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=html5&logoColor=white) | Markup |
| ![CSS3](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=css3&logoColor=white) | Styling |
| ![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white) | UI components |
| ![Axios](https://img.shields.io/badge/Axios-5A29E4?style=for-the-badge&logo=axios&logoColor=white) | HTTP client |
| ![React Router](https://img.shields.io/badge/React%20Router-CA4245?style=for-the-badge&logo=reactrouter&logoColor=white) | Client-side routing |

## 🗄️ Databases & Storage

| Technology | Purpose |
|---|---|
| ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white) | Primary transactional database |
| ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white) | Audit and activity storage |
| ![Redis](https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white) | Cache and temporary seat locking |

## 📨 Messaging

| Technology | Purpose |
|---|---|
| ![Apache Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white) | Event-driven communication |

## 🧪 Testing & API Tools

| Technology | Purpose |
|---|---|
| ![JUnit](https://img.shields.io/badge/JUnit-5-25A162?style=for-the-badge&logo=junit5&logoColor=white) | Unit and integration testing |
| Mockito | Mocking |
| Postman | API testing |
| Swagger/OpenAPI | API documentation |

## 🔧 Development Tools

- Git
- GitHub
- IntelliJ IDEA
- MySQL Workbench
- MongoDB Compass
- Redis / Memurai
- Apache Kafka
- Node.js
- npm

---

# 🏗️ Architecture

The application follows a layered full-stack architecture.

**Frontend → REST API → Spring Boot → Services → Repositories → Databases / Messaging / Cache**

### High-Level Architecture

    ┌─────────────────────────────────────────┐
    │              React Frontend             │
    │                                         │
    │ React + Axios + React Router + Bootstrap│
    └────────────────────┬────────────────────┘
                         │
                      REST APIs
                         │
                         ▼
    ┌─────────────────────────────────────────┐
    │            Spring Boot Backend          │
    │                                         │
    │ Controllers                             │
    │ Services                                │
    │ DTOs                                    │
    │ Validation                              │
    │ Spring Security + JWT                   │
    └───────────────┬─────────────┬───────────┘
                    │             │
                    ▼             ▼
             ┌────────────┐   ┌────────────┐
             │   MySQL    │   │  MongoDB   │
             │            │   │            │
             │ Users      │   │ Audit Logs │
             │ Flights    │   │ Activity   │
             │ Seats      │   │ History    │
             │ Bookings   │   │            │
             │ Payments   │   │            │
             └────────────┘   └────────────┘
                    │
              ┌─────┴─────┐
              ▼           ▼
        ┌──────────┐  ┌──────────────┐
        │  Redis   │  │ Apache Kafka │
        │          │  │              │
        │ Cache    │  │ Events       │
        │ Seat Lock│  │ Messaging    │
        │ TTL      │  │              │
        └──────────┘  └──────────────┘

---

# 📁 Project Structure

    Airline-Reservation-System/
    │
    ├── backend/
    │   ├── src/
    │   │   ├── main/
    │   │   │   ├── java/
    │   │   │   │   └── com/
    │   │   │   │       └── airline/
    │   │   │   │           └── reservation/
    │   │   │   │               ├── config/
    │   │   │   │               ├── controller/
    │   │   │   │               ├── dto/
    │   │   │   │               │   ├── request/
    │   │   │   │               │   └── response/
    │   │   │   │               ├── entity/
    │   │   │   │               ├── enums/
    │   │   │   │               ├── exception/
    │   │   │   │               ├── mapper/
    │   │   │   │               ├── repository/
    │   │   │   │               ├── security/
    │   │   │   │               ├── service/
    │   │   │   │               │   └── impl/
    │   │   │   │               └── util/
    │   │   │   └── resources/
    │   │   │       └── application.properties
    │   │   │
    │   │   └── test/
    │   │       └── java/
    │   │
    │   └── pom.xml
    │
    ├── frontend/
    │   ├── public/
    │   ├── src/
    │   │   ├── assets/
    │   │   ├── components/
    │   │   ├── pages/
    │   │   ├── layouts/
    │   │   ├── services/
    │   │   ├── context/
    │   │   ├── hooks/
    │   │   ├── routes/
    │   │   ├── utils/
    │   │   ├── App.jsx
    │   │   └── main.jsx
    │   │
    │   └── package.json
    │
    ├── docs/
    ├── .gitignore
    └── README.md

---

# 🧩 Application Modules

## 👤 User Module

- User registration
- User login
- Authentication
- Profile management
- User roles
- Account status
- Booking history

## 🏢 Airport Module

- Airport creation
- Airport information
- Airport updates
- Airport search
- Airport management

## ✈️ Aircraft Module

- Aircraft creation
- Aircraft information
- Aircraft status
- Aircraft management

## 🛫 Flight Module

- Flight creation
- Flight updates
- Flight status
- Source and destination
- Departure and arrival
- Flight search
- Flight filtering
- Seat availability

## 💺 Seat Module

- Seat creation
- Seat class management
- Seat status management
- Seat availability
- Seat selection
- Temporary seat locking
- Seat booking
- Seat release

## 👨‍👩‍👧 Passenger Module

- Passenger creation
- Passenger details
- Passport information
- Passenger updates
- Passenger association with bookings

## 🎫 Booking Module

- Booking creation
- PNR generation
- Passenger association
- Seat association
- Fare calculation
- Booking confirmation
- Booking history
- Booking cancellation

## 💳 Payment Module

- Payment creation
- Mock payment processing
- Payment success/failure
- Payment status
- Refund status
- Booking-payment relationship

## 🔐 Authentication & Authorization Module

- User login
- Admin login
- JWT access tokens
- Refresh tokens
- Logout
- BCrypt password hashing
- USER role
- ADMIN role
- Protected endpoints
- User ownership authorization
- 401 Unauthorized handling
- 403 Forbidden handling

## 📝 Audit & Activity Module

MongoDB is used for storing application activity and audit-related information.

- Audit events
- User activity
- Flight search history
- Booking activity
- Authentication activity

## ⚡ Redis Module

Redis is used for:

- Temporary seat locks
- TTL-based locking
- Cache management
- Seat availability caching
- Preventing simultaneous seat selection conflicts

## 📨 Kafka Module

Kafka is used for event-driven communication.

### Kafka Topics

- `booking-created`
- `booking-cancelled`
- `payment-success`
- `payment-failed`
- `flight-updated`

---

# 🔌 API Modules

## 👤 User APIs

- `POST /api/users`
- `GET /api/users`
- `GET /api/users/{id}`
- `PUT /api/users/{id}`

## 🏢 Airport APIs

- `POST /api/airports`
- `GET /api/airports`
- `GET /api/airports/{id}`
- `PUT /api/airports/{id}`
- `DELETE /api/airports/{id}`

## ✈️ Aircraft APIs

- `POST /api/aircraft`
- `GET /api/aircraft`
- `GET /api/aircraft/{id}`
- `PUT /api/aircraft/{id}`
- `DELETE /api/aircraft/{id}`

## 🛫 Flight APIs

- `POST /api/flights`
- `GET /api/flights`
- `GET /api/flights/{id}`
- `PUT /api/flights/{id}`
- `DELETE /api/flights/{id}`

## 💺 Seat APIs

- `POST /api/seats`
- `GET /api/seats/{id}`
- `GET /api/seats/flight/{flightId}`
- `PUT /api/seats/{id}`

## 👨‍👩‍👧 Passenger APIs

- `POST /api/passengers`
- `GET /api/passengers/{id}`
- `GET /api/passengers/passport/{passportNo}`
- `GET /api/passengers/user/{userId}`
- `PUT /api/passengers/{id}`

## 🎫 Booking APIs

- `POST /api/bookings`
- `GET /api/bookings/{id}`
- `GET /api/bookings/reference/{pnr}`
- `GET /api/bookings/user/{userId}`
- `PATCH /api/bookings/{id}/cancel`

## 💳 Payment APIs

- `POST /api/payments`
- `GET /api/payments/booking/{bookingId}`

---

# 🗄️ Database Architecture

## MySQL

MySQL is the primary transactional database.

### Main Tables

- `users`
- `airports`
- `aircraft`
- `flights`
- `seats`
- `passengers`
- `bookings`
- `payments`
- `booking_seats`
- `booking_passengers`

### Database Relationships

    User
     │
     └──< Booking
              │
              ├──< Passenger
              ├──< Seat
              └── Payment

    Airport ───< Flight >─── Airport

    Aircraft ───< Flight

    Flight ───< Seat

---

# 🔄 Booking Workflow

    User Searches Flight
            ↓
    Selects Flight
            ↓
    Checks Seat Availability
            ↓
    Selects Seat(s)
            ↓
    Temporary Seat Lock
            ↓
    Passenger Details
            ↓
    Booking Created
            ↓
    Mock Payment
            ↓
       ┌───────────────┐
       │ Payment Result│
       └───────┬───────┘
               │
         ┌─────┴─────┐
         ↓           ↓
      SUCCESS      FAILURE
         │           │
         ↓           ↓
      Booking      Booking
     CONFIRMED     Pending
         │           │
         ↓           ↓
     Seats BOOKED  Seats Released
         │
         ↓
    Payment SUCCESS
         │
         ↓
    PNR / Ticket Confirmation

### Booking Cancellation

    Confirmed Booking
           ↓
    Cancellation Request
           ↓
    Booking CANCELLED
           ↓
    Seats RELEASED
           ↓
    Refund Status Updated

---

# 🔐 Security Architecture

The application uses **Spring Security, JWT, BCrypt, and role-based access control**.

    User
     │
     ▼
    Login
     │
     ▼
    Spring Security
     │
     ▼
    Credential Validation
     │
     ▼
    JWT Access Token
     │
     ▼
    Protected API
     │
     ▼
    JWT Validation
     │
     ▼
    Role / Ownership Check
     │
     ├── USER
     │
     └── ADMIN

### Security Features

- BCrypt password hashing
- JWT authentication
- Refresh token support
- Logout
- Role-based access control
- Endpoint protection
- User ownership checks
- CORS configuration
- 401 Unauthorized handling
- 403 Forbidden handling

---

# 💻 Local Development

## 1️⃣ Prerequisites

Install the following:

- Java 21
- Maven
- MySQL
- MongoDB
- Redis / Memurai
- Apache Kafka
- Node.js
- npm
- Git
- IntelliJ IDEA
- MySQL Workbench
- MongoDB Compass
- Postman

## 2️⃣ Clone the Repository

    git clone https://github.com/mr-piyushkr/Airline-Reservation-System.git

    cd Airline-Reservation-System


## 3️⃣ MySQL Setup

Create the database:

    CREATE DATABASE airline_reservation;

Verify:

    SHOW DATABASES;

## 4️⃣ MongoDB Setup

Make sure MongoDB is running locally.

Default connection:

    mongodb://localhost:27017

## 5️⃣ Redis Setup

Default configuration:

    Host: localhost
    Port: 6379

Test Redis:

    PING

Expected response:

    PONG

## 6️⃣ Kafka Setup

Default Kafka broker:

    localhost:9092

Required topics:

- `booking-created`
- `booking-cancelled`
- `payment-success`
- `payment-failed`
- `flight-updated`

## 7️⃣ Run Backend

Navigate to the backend:

    cd backend

Build the project:

    mvn clean package

Run the application:

    mvn spring-boot:run

Backend:

    http://localhost:8080

## 8️⃣ Run Frontend

Navigate to the frontend:

    cd frontend

Install dependencies:

    npm install

Start the development server:

    npm run dev

The frontend URL will be displayed in the terminal.

---

# ⚙️ Configuration

Example backend configuration:

    server.port=8080

    spring.datasource.url=jdbc:mysql://localhost:3306/airline_reservation
    spring.datasource.username=YOUR_USERNAME
    spring.datasource.password=YOUR_PASSWORD

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

    spring.data.mongodb.uri=mongodb://localhost:27017/airline_audit

    spring.data.redis.host=localhost
    spring.data.redis.port=6379

    spring.kafka.bootstrap-servers=localhost:9092

### ⚠️ Security

Never commit real credentials, passwords, JWT secrets, API keys, or other sensitive information to GitHub.

Use environment variables or local configuration files excluded through `.gitignore`.

---

# 🧪 Testing

The application is designed to support unit, integration, security, and API testing.

### Testing Areas

- User registration
- User login
- JWT authentication
- Authorization
- Flight search
- Flight filtering
- Seat availability
- Seat locking
- Passenger creation
- Booking creation
- Payment success
- Payment failure
- Booking cancellation
- Redis integration
- Kafka events
- MongoDB audit logs
- Admin authorization
- User ownership protection

### Testing Tools

- JUnit 5
- Mockito
- Spring Boot Test
- MockMvc
- Postman
- Swagger/OpenAPI

---

# 📚 API Documentation

Swagger/OpenAPI can be used to document and test the REST APIs.

Typical local URL:

    http://localhost:8080/swagger-ui/index.html

---

# 🚀 Future Enhancements

- Real payment gateway integration
- Real airline/GDS API integration
- Email ticket confirmation
- SMS notifications
- PDF ticket generation
- Multi-language support
- Advanced flight filters
- Fare comparison
- Coupon and discount system
- Loyalty/reward points
- Online check-in
- Baggage management
- Boarding pass generation
- Advanced admin analytics
- Real-time flight status
- Notification service
- Production cloud deployment
- Containerization
- CI/CD pipeline
- Kubernetes deployment
- Monitoring and observability

---

# 🤝 Contributing

Contributions are welcome.

### Contribution Steps

1. Fork the repository.
2. Clone your fork.
3. Create a feature branch.
4. Make your changes.
5. Commit your changes.
6. Push the branch.
7. Open a Pull Request.

Example:

    git clone <your-fork-url>

    git checkout -b feature/your-feature

    git add .

    git commit -m "Add your feature"

    git push origin feature/your-feature

---

# ⭐ Show Your Support

If you like this project:

- ⭐ Star the repository
- 🍴 Fork the project
- 🐛 Report bugs
- 💡 Suggest improvements
- 🤝 Contribute to the project

---

# 📬 Let's Connect

<div align="center">

<a href="https://my-portfolio-umber-zeta-11.vercel.app/" target="_blank">
  <img src="https://img.shields.io/badge/Portfolio-Visit%20Portfolio-000000?style=for-the-badge&logo=vercel&logoColor=white" alt="Portfolio"/>
</a>

<a href="https://github.com/mr-piyushkr" target="_blank">
  <img src="https://img.shields.io/badge/GitHub-mr--piyushkr-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub"/>
</a>

<a href="https://linkedin.com/in/piyushkumar06" target="_blank">
  <img src="https://img.shields.io/badge/LinkedIn-Piyush%20Kumar-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white" alt="LinkedIn"/>
</a>

<a href="mailto:0602.piyushkumar@gmail.com">
  <img src="https://img.shields.io/badge/Email-Contact%20Me-EA4335?style=for-the-badge&logo=gmail&logoColor=white" alt="Email"/>
</a>

</div>

---

# 👨‍💻 Author

## Piyush Kumar

**Full Stack Developer**

I build full-stack applications using Java, Spring Boot, React, SQL, REST APIs, and modern software engineering practices.


---

# 📄 License

This project is licensed under the **MIT License**.

You are free to use, modify, distribute, and build upon this project in accordance with the terms of the license.

---

<div align="center">

## ✈️ Airline Reservation System

**Built with Java + Spring Boot + React**

⭐ Star the repository if you like the project!

Made with ❤️ by **Piyush Kumar**

</div>
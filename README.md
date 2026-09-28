# Airline Reservation System — Backend

## Tech Stack
- Java 21, Spring Boot 3.3.5, Spring MVC, Spring Data JPA, Hibernate, MySQL, Lombok, Jakarta Validation, Maven

---

## Local Setup

### 1. MySQL Configuration
```sql
CREATE DATABASE IF NOT EXISTS airline_reservation;
```
Update `src/main/resources/application.properties`:
```properties
spring.datasource.username=root
spring.datasource.password=YOUR_PASSWORD
```

### 2. Run
```bash
./mvnw spring-boot:run
```
Server: `http://localhost:8080`

---

## Package Structure
```
com.airline.reservation
├── controller       # REST controllers
├── service          # Interfaces + impl/
├── repository       # Spring Data JPA repositories
├── entity           # JPA entities
├── dto
│   ├── request      # Validated request DTOs
│   └── response     # Response DTOs
├── mapper           # Entity <-> DTO mappers
├── exception        # Custom exceptions + GlobalExceptionHandler
└── enums            # UserRole, BookingStatus, PaymentStatus, SeatStatus, FlightStatus, SeatClass
```

---

## Complete Booking Workflow

```
POST /api/flights/search          → Search available flights
GET  /api/seats/flight/{id}/available → View available seats
POST /api/passengers              → Create passenger(s)
POST /api/bookings                → Create booking → PNR generated (PENDING)
POST /api/payments                → Process mock payment → CONFIRMED + seats BOOKED
GET  /api/bookings/reference/{pnr}→ View booking details
GET  /api/bookings/user/{userId}  → Booking history
PATCH /api/bookings/{id}/cancel   → Cancel → seats released → payment REFUNDED
```

---

## REST APIs

### Users — `/api/users`
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/users` | Create user |
| GET | `/api/users` | Get all users |
| GET | `/api/users/{id}` | Get user by ID |
| PUT | `/api/users/{id}` | Update user |
| DELETE | `/api/users/{id}` | Delete user |

### Airports — `/api/airports`
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/airports` | Create airport |
| GET | `/api/airports` | Get all airports |
| GET | `/api/airports/{id}` | Get by ID |
| GET | `/api/airports/iata/{code}` | Get by IATA code |
| PUT | `/api/airports/{id}` | Update |
| DELETE | `/api/airports/{id}` | Delete |

### Aircraft — `/api/aircraft`
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/aircraft` | Create aircraft |
| GET | `/api/aircraft` | Get all |
| GET | `/api/aircraft/{id}` | Get by ID |
| PUT | `/api/aircraft/{id}` | Update |
| DELETE | `/api/aircraft/{id}` | Delete |

### Flights — `/api/flights`
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/flights` | Create flight |
| GET | `/api/flights` | Get all |
| GET | `/api/flights/{id}` | Get by ID |
| GET | `/api/flights/number/{number}` | Get by flight number |
| GET | `/api/flights/search?origin=DEL&destination=BOM&date=2026-12-01` | Search flights |
| PUT | `/api/flights/{id}` | Update |
| DELETE | `/api/flights/{id}` | Delete |

### Seats — `/api/seats`
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/seats` | Create seat for a flight |
| GET | `/api/seats/{id}` | Get seat by ID |
| GET | `/api/seats/flight/{flightId}` | All seats for flight |
| GET | `/api/seats/flight/{flightId}/available` | Available seats |
| GET | `/api/seats/flight/{flightId}/class/{class}` | Seats by class |

### Passengers — `/api/passengers`
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/passengers` | Create passenger |
| GET | `/api/passengers/{id}` | Get by ID |
| GET | `/api/passengers/passport/{number}` | Get by passport |
| GET | `/api/passengers/user/{userId}` | Get by user |
| PUT | `/api/passengers/{id}` | Update passenger |

### Bookings — `/api/bookings`
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/bookings` | Create booking + generate PNR |
| GET | `/api/bookings/{id}` | Get booking by ID |
| GET | `/api/bookings/reference/{pnr}` | Get by PNR |
| GET | `/api/bookings/user/{userId}?status=CONFIRMED` | Booking history (optional filter) |
| PATCH | `/api/bookings/{id}/cancel` | Cancel booking |

### Payments — `/api/payments`
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/payments` | Process mock payment |
| GET | `/api/payments/booking/{bookingId}` | Get payment for booking |

---

## Error Response Format
```json
{
  "timestamp": "2026-12-01T08:00:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Seats are not available: 10A",
  "path": "/api/bookings",
  "fieldErrors": null
}
```

---

## NOT Implemented Yet (Phase 3+)
- Spring Security + JWT authentication
- BCrypt password hashing
- RBAC (role-based access control)
- MongoDB audit logs
- Redis seat locking
- Kafka event-driven processing
- React frontend

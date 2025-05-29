
# 🧠 Wortcat API - Vocabulary Learning App Backend

A Spring Boot RESTful API backend for a vocabulary learning app. It allows users to create and manage vocabulary cards grouped into decks, track learning stats, and access learning tools like spaced repetition and guessing games.

---

## 📦 Features

- 🔐 Secure user authentication with **JWT (JSON Web Token)**
- 📚 Manage vocabulary cards (create, update, delete, retrieve, search)
- 🗂️ Organize cards into decks
- 📈 Track learning stats and spaced repetition progress
- 🧾 Role-based access control
- 🕒 Scheduled stats updates via a background task
- 🧪 RESTful API with clean MVC structure

---

## 🧱 Tech Stack

- Java 24
- Spring Boot
- Spring Security (JWT)
- SM2 Algorithm (for spaced repitition)
- Spring Data JPA
- PostgreSQL 
- Lombok
- Maven

---

## 🏗️ Project Structure

```
com.Alasww.wortcat_API
├── auth/              # JWT  authentication
├── card/              # Vocabulary cards logic
├── deck/              # Deck management
├── stats/             # User learning stats and scheduling
├── user/              # User and role management
├── config/            # JWT, SM2 and security configuration
└── WortcatApiApplication.java
```

---

## ⚙️ Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL 

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/AlaSww/wortcat-api.git
   cd wortcat-api
   ```

2. Configure your environment in `application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/wortcat
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password

   jwt.secret=your_jwt_secret
   jwt.expiration=86400000
   ```

3. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```

---

## 🔐 Security

This project uses :

- **JWT Authentication**: All endpoints are protected and require valid tokens for access.

Tokens must be included in the `Authorization` header as:
```
Authorization: Bearer <your_token>
```

---

## 🧪 API Overview

### Auth

- `POST /api/auth/register` – Register a new user
- `POST /api/auth/authenticate` – Login with credentials and receive a JWT token

### Cards

- `GET /api/v1/cards` – Fetch all cards
- `POST /api/v1/deck/{deckId}/cards` – Create a new card
- `PUT /api/v1/deck/{deckId}/cards/{cardId}` – Update a card
- `DELETE /api/v1/deck/{deckId}/cards/{cardId}` – Delete a card

### Decks

- `GET /api/v1/decks` – Get all decks
- `POST /api/v1/decks` – Create a new deck
- `GET /api/v1/decks/{id}/cards` – Get cards in a deck

### Stats

- `GET /api/v1/stats` – View learning stats
- Auto-updated by the `StatScheduler` using spaced repetition logic

---

## 📅 Scheduled Tasks

- `StatScheduler`: Runs in the background to periodically update card statuses and review statistics based on learning progress.

---

## 🧪 Testing

Run unit and integration tests:
```bash
./mvnw test
```

---

## 📬 Contact

**Ala S.**  
GitHub: [github.com/AlaSww](https://github.com/AlaSww)  
Email: alaeddinebensalem01@gmail.com

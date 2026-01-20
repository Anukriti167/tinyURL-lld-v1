# TinyURL – Low Level Design (LLD)

This document describes the **Low Level Design (LLD)** for a simplified **TinyURL / URL Shortener** system.

The design is intentionally **minimal, clean, and interview-focused**, suitable for **30–40 minute SDE-2 LLD interviews**.

---

## 🎯 Problem Statement

Design a system that:

* Converts a long URL into a short URL
* Redirects a short URL to the original long URL
* Ensures uniqueness and fast lookups

---

## 📌 Scope & Assumptions

### In Scope

* URL shortening
* URL redirection
* Deterministic short code generation
* Fast read performance

### Out of Scope

* User authentication
* Analytics (click counts, geo stats)
* URL expiration
* Custom aliases
* Rate limiting

> These can be added later as extensions if required.

---

## 🔹 Functional Requirements

1. Generate a short URL for a given long URL
2. Redirect a short URL to its original URL
3. Return the same short URL if the same long URL is shortened again

---

## 🔹 Non‑Functional Requirements

* Low latency (read-heavy system)
* High availability
* Reasonably short URL length
* Collision-free generation

---

## 🧠 Core Design Decisions

### 🔑 Short Code Generation Strategy

**Incremental ID + Base62 Encoding**

* Maintain a monotonically increasing counter
* Encode the counter using Base62 (`a–z`, `A–Z`, `0–9`)
* Guarantees uniqueness
* Easy to explain and debug

This avoids:

* Hash collisions
* Random UUID complexity

---

## 🧩 Core Components

### 1️⃣ UrlMapping (Domain Model)

Represents the mapping between a short code and a long URL.

**Responsibilities**

* Store shortCode → longUrl mapping
* Immutable data object

---

### 2️⃣ IdGenerator (Strategy)

Abstracts ID generation logic.

**Why?**

* Allows changing generation strategy later
* Keeps TinyUrlService clean

Example implementation:

* Base62IdGenerator

---

### 3️⃣ TinyUrlService (Business Logic)

**Responsibilities**

* Generate short URLs
* Resolve short URLs
* Prevent duplicates

**Internal Data Structures**

* `Map<shortCode, longUrl>`
* `Map<longUrl, shortCode>`

---

### 4️⃣ TinyUrlController (REST Layer)

**Responsibilities**

* Expose REST APIs
* Delegate logic to service
* Handle redirects

---

## 🔌 API Design

### 🔹 Create Short URL

```
POST /shorten
```

**Request**

```json
{
  "longUrl": "https://example.com/very/long/url"
}
```

**Response**

```json
{
  "shortUrl": "http://tiny.ly/aZ3x9"
}
```

---

### 🔹 Redirect to Long URL

```
GET /{shortCode}
```

**Behavior**

* Returns HTTP 302 redirect
* Redirects to original long URL

---

## 🧱 Text‑Based Class Diagram

```
TinyUrlController
 └── TinyUrlService
       ├── IdGenerator
       └── UrlMapping
```

---

## 🧠 Edge Cases & Handling

| Scenario                 | Handling                   |
| ------------------------ | -------------------------- |
| Same URL shortened twice | Return existing short URL  |
| Short code not found     | 404 Not Found              |
| Collision                | Avoided via incremental ID |

---

## 🔁 Extensibility (Optional Enhancements)

These can be added without redesigning core logic:

* URL expiration
* Custom aliases
* Click analytics
* Rate limiting
* Persistence using DB / Redis

---

## 🎤 Interview Notes

If asked **why this design works well**:

> "The domain is intentionally minimal. I use a deterministic Base62 encoding strategy to avoid collisions and separate concerns cleanly between controller, service, and utility layers."

This highlights:

* Clear abstraction
* Good trade‑off judgment
* SDE‑2 maturity

---

## ✅ Summary

This TinyURL LLD:

* Solves the core problem cleanly
* Avoids over‑engineering
* Is easy to explain and defend
* Scales naturally with extensions

---


# ☕ Franchise API

This project implements a **backend REST API** for managing **franchises, branches, and products** using **Spring Boot**.
It includes full CRUD operations, stock management, and reporting endpoints (e.g., top-stock products per franchise).

---

## 🚀 Project Description

Each **Franchise** has:

* A name
* A list of **Branches**

Each **Branch** has:

* A name
* A city
* A list of **Products**

Each **Product** has:

* A name
* A price
* A stock quantity
* A category

---

## 🧰 Tech Stack

* **Language:** Java 17+
* **Framework:** Spring Boot 3.5.6
* **Database:** MySQL (production) / H2 (testing)
* **Build Tool:** Maven
* **Version Control:** Git + GitHub
* **Containerization:** Docker
* **Testing:** JUnit 5 + Mockito + MockMvc + H2 (integration tests)
* **Coverage:** JaCoCo

---

## ⚙️ Local Setup Instructions

### 1️⃣ Clone the repository

```bash
git clone https://github.com/sntgcrdn/franchise-api.git
cd franchise-api
```

### 2️⃣ Build and run the project

```bash
mvn clean package
mvn spring-boot:run   # Run locally (without Docker)
```

### 3️⃣ Run with Docker

```bash
docker build -t franchise-api .
docker run -p 8080:8080 franchise-api
```

### 4️⃣ Run tests

```bash
mvn test
```

🧹 Test coverage report:

```
target/site/jacoco/index.html
```

### 5️⃣ Access the API

```
http://localhost:8080
```

---

## 🌐 Endpoints

### 🏢 Franchise Endpoints

| Method | Endpoint                              | Description                                              |
| ------ | ------------------------------------- | -------------------------------------------------------- |
| GET    | `/franchises`                         | Get all franchises                                       |
| GET    | `/franchises/{id}`                    | Get a franchise by ID                                    |
| POST   | `/franchises`                         | Create a new franchise                                   |
| PUT    | `/franchises/{id}`                    | Update franchise data (name, owner, etc.)                |
| PATCH  | `/franchises/{id}/updatename`         | Update only the franchise name                           |
| DELETE | `/franchises/{id}`                    | Delete a franchise                                       |
| GET    | `/franchises/{id}/top-stock-products` | Get the top-stock product for each branch in a franchise |

---

### 🏪 Branch Endpoints

| Method | Endpoint                    | Description                 |
| ------ | --------------------------- | --------------------------- |
| GET    | `/branches`                 | Get all branches            |
| GET    | `/branches/{id}`            | Get a branch by ID          |
| POST   | `/branches`                 | Create a new branch         |
| PUT    | `/branches/{id}`            | Update branch details       |
| PATCH  | `/branches/{id}/updatename` | Update only the branch name |
| DELETE | `/branches/{id}`            | Delete a branch             |

---

### 📦 Product Endpoints

| Method | Endpoint                    | Description                   |
| ------ | --------------------------- | ----------------------------- |
| GET    | `/products`                 | Get all products              |
| GET    | `/products/{id}`            | Get a product by ID           |
| POST   | `/products`                 | Create a new product          |
| PUT    | `/products/{id}`            | Update product details        |
| PATCH  | `/products/{id}/stock`      | Update only the product stock |
| PATCH  | `/products/{id}/updatename` | Update only the product name  |
| DELETE | `/products/{id}`            | Delete a product              |

---

## ⚙️ Configuration (`application.properties`)

```properties
# --- Database Configuration (H2 for local/integration testing) ---
spring.datasource.url=jdbc:h2:mem:franchise_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# --- Hibernate / JPA ---
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🥉 H2 Console Access

```
http://localhost:8080/h2-console
```

Use the same JDBC URL as above:

```
jdbc:h2:mem:franchise_db
```

---

## 🧪 Running Integration Tests with H2

Integration tests are configured to use an **in-memory H2 database**.
This ensures isolation from production data and faster test execution.

### ✅ Create the file `src/test/resources/application-test.properties`

```properties
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
```

### ✅ Annotate your integration test classes with:

```java
@SpringBootTest
@ActiveProfiles("test")
```

This tells Spring Boot to load the test configuration automatically.
When you run `mvn test`, all integration tests will use the H2 database in memory.

---

## 🐳 Docker Image

You can pull and run the latest version of the API directly from **Docker Hub**.

### 🏷️ Available Versions

| Tag | Description | Pull Command |
|-----|--------------|---------------|
| `latest` | Latest stable release | `docker pull sancar0/franchise-api:latest` |
| `v1.0.0` | Initial public release | `docker pull sancar0/franchise-api:v1.0.0` |

### ▶️ Run the API with Docker

```bash
docker run -d -p 8080:8080 sancar0/franchise-api:v1.0.0
Then access it at: http://localhost:8080

👉 Docker Hub page: https://hub.docker.com/r/sancar0/franchise-api
```
---

---

## 📬 Postman Collection

You can test the API endpoints using Postman.
A ready-to-use collection is included in:

```
/postman/FranchiseAPI.postman_collection.json
```

### Import Instructions

1. Open **Postman**
2. Click **Import** → select the JSON file
3. Make sure your API is running (Docker or local)
4. Set the variable:

   ```
   baseUrl = http://localhost:8080
   ```
5. Run the requests for franchises, branches, and products

---

## 🧠 Author

**Santiago Cardona**
📧 [sntgcrdnq@gmail.com](mailto:sntgcrdnq@gmail.com)
💼 GitHub: [@sntgcrdn](https://github.com/sntgcrdn)

---

## 🟞 License

MIT License © 2025
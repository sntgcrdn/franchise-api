# Franchise API

This project implements a backend API for managing franchises, branches, and products using **Spring Boot**.

## 🚀 Project Description
Each **Franchise** has:
- A name
- A list of **Branches**

Each **Branch** has:
- A name
- A list of **Products**

Each **Product** has:
- A name
- A stock quantity

---

## ⚙️ Features (Acceptance Criteria)
- [x] Add a new franchise
- [x] Add a new branch to a franchise
- [x] Add a new product to a branch
- [x] Delete a product from a branch
- [x] Modify a product’s stock
- [x] Get the product with the highest stock per branch in a franchise

### 🧩 Bonus Features
- [ ] Update franchise name
- [ ] Update branch name
- [ ] Update product name
- [ ] Cloud deployment (optional)
- [ ] Infrastructure as code (Terraform, CloudFormation)
- [ ] Functional or reactive programming (optional)

---

## 🧰 Tech Stack
- **Language:** Java 17+  
- **Framework:** Spring Boot 3.x  
- **Database:** H2 (in-memory)  
- **Build Tool:** Maven  
- **Version Control:** Git + GitHub  
- **Containerization:** Docker (optional)

---

## 🧑‍💻 Local Setup Instructions

### 1️⃣ Clone the repository
```bash
git clone https://github.com/sntgcrdn/franchise-api.git
cd franchise-api
```

### 2️⃣ Build and run the project
```bash
./mvnw spring-boot:run
```

### 3️⃣ Access the API
Default URL:  
```
http://localhost:8080
```

---

## 🧱 Example Endpoints

| Method | Endpoint | Description |
|--------|-----------|-------------|
| POST | `/api/franchises` | Add new franchise |
| POST | `/api/franchises/{id}/branches` | Add branch to franchise |
| POST | `/api/franchises/{id}/branches/{branchId}/products` | Add product to branch |
| DELETE | `/api/franchises/{id}/branches/{branchId}/products/{productId}` | Delete product |
| PUT | `/api/franchises/{id}/branches/{branchId}/products/{productId}` | Update product stock |
| GET | `/api/franchises/{id}/top-products` | Get product with highest stock per branch |

---

## 🧩 Database Configuration (Option 1: In-Memory H2)
This project uses **H2 Database** to simplify setup during development.

### Configuration (`application.properties`)
```properties
spring.datasource.url=jdbc:h2:mem:franchise_db
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Access H2 Console
```
http://localhost:8080/h2-console
```
Use the same JDBC URL as above.

---

## 🐳 Run with Docker (optional)
```bash
docker build -t franchise-api .
docker run -p 8080:8080 franchise-api
```

---

## 🧾 Git Usage Workflow

### Initialize local repository
```bash
git init
git add .
git commit -m "Initial commit"
```

### Add remote repository
```bash
git remote add origin https://github.com/sntgcrdn/franchise-api.git
git branch -M main
git push -u origin main
```

### Update repository
```bash
git add .
git commit -m "Update project"
git push
```

---

## 🧠 Author
**Santiago Cardona**  
📍 El Carmen de Viboral, Antioquia, Colombia  
📧 [sntgcrdnq@gmail.com](mailto:sntgcrdnq@gmail.com)

---

## 🧩 License
MIT License © 2025 Santiago Cardona


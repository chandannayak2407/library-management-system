📚 Library Management System
A dynamic Library Management System built using HTML, CSS, and JavaScript for the frontend, Spring Boot (Java) for the backend, and MySQL for the database. This system helps manage books, staff, students, and transactions efficiently.

📌 Features
🔐 Admin and Student Login

📚 Book Management (Add, Edit, Delete, Search)

👨‍🏫 Staff Management

🎓 Student Management

🔁 Book Transactions (Issue/Return)

🔑 Change Password

📊 Dashboard with analytics

🧰 Technologies Used
Layer	Tech Stack
Frontend	HTML, CSS, JavaScript
Backend	Spring Boot (Java)
Database	MySQL
Deployment	AWS (Optional)

🗂️ Project Structure
css
Copy
Edit
library-management-system/
├── frontend/
│   ├── index.html
│   ├── login.html
│   ├── dashboard.html
│   └── ...
├── backend/
│   ├── src/
│   │   └── main/java/com/lms/
│   └── ...
├── database/
│   └── lms_schema.sql
└── README.md
⚙️ Setup Instructions
🖥️ Prerequisites
Java 17+

Node.js (optional for future frontend bundling)

MySQL Server

Spring Boot & Maven

🔧 Backend Setup
bash
Copy
Edit
cd backend
mvn clean install
mvn spring-boot:run
🌐 Frontend Setup
Open frontend/index.html in your browser or use Live Server extension in VS Code.

🛢️ Database Setup
Create a MySQL database named lms

Import the SQL file:

sql
Copy
Edit
source path/to/lms_schema.sql;
Update application.properties in Spring Boot with your DB credentials

✅ Future Improvements
Add JWT-based authentication

Integrate email notification system

Add student request book feature

Improve UI/UX with Bootstrap or React.js

👨‍🎓 Project Info
🎓 College: Gurukul Management Studies

🧑‍💻 Student: Chandan Nayak

📅 Year: 2025 (Major Project - BCA)


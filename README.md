🏠 Home Heaven – Smart Rental Property Finder

Home Heaven is a modern, user-friendly web application that connects property owners directly with tenants (students, employees, and working professionals) looking for rental accommodations such as PGs, hostels, flats, and houses — without brokers.

The platform allows users to search properties by location, budget, and type, while owners can easily upload and manage their properties from a single dashboard.

🚀 Features
👤 User / Seeker

User Registration & Login

Secure Password Reset using 4-Digit PIN

Search properties by:

Location

Budget range

Property type (PG / Hostel / Flat / House)

View property details:

Images

Rent

Address

Description

Owner contact details

🏠 Property Owner

Upload property with:

Multiple images

Property type

Rent

Square footage (Flats)

Sharing options (PGs)

Minimum 100-word description

View My Properties section

Direct contact with interested tenants (no broker)

🛡️ Admin Panel (View-Only)

Fixed Admin Credentials (pre-configured)

View total:

Registered users

Uploaded properties

View all users and properties

No edit or delete permissions (read-only)

🧰 Tech Stack
Backend

Java

Spring Boot

Spring Security

REST APIs

MySQL (SQL Database)

Frontend

HTML5

CSS3

JavaScript (Vanilla)

Responsive UI (Red Theme)

🖥️ Project Runs On

Localhost only

No cloud or production deployment required

🔐 Default Admin Credentials

Email: admin@homeheavan.com

Password: Admin@123

⚙️ How to Run the Project

1️⃣ Prerequisites

Java JDK 17+

MySQL

VS Code / IntelliJ IDEA

2️⃣ Database Setup

Create a database in MySQL:

CREATE DATABASE homeheaven;


Update credentials in:

src/main/resources/application.properties

3️⃣ Run Backend

If Maven is installed:

mvn spring-boot:run


OR open the project in IDE and run:

HomeHeavenApplication.java

4️⃣ Access Application

http://localhost:8080/login.html

📂 Folder Structure 

homeheaven/

│

├── src/main/java/

│   ├── controller/

│   ├── service/

│   ├── repository/

│   └── model/

│

├── src/main/resources/

│   ├── static/

│   │   ├── dashboard.html

│   │   ├── upload-property.html

│   │   ├── admin.html

│   │   └── css/

│   └── application.properties

│

└── README.md

📌 Future Enhancements

OTP via SMS / Email

Property approval system

Filters by amenities

Cloud deployment

Mobile app version

👨‍💻 Author

Home Heaven Project

Built for academic and learning purposes ❤️

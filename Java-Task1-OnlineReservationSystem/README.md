# Online Reservation System

A Java Swing-based Online Reservation System developed as part of the **Oasis Infobyte Java Development Internship – Task 1**.

The application allows users to log in, book train tickets, generate unique PNR numbers, fetch reservation details using a PNR number, and cancel reservations.

---

## 📌 Project Objective

The objective of this project is to develop a GUI-based train reservation system using Java Swing, JDBC, and MySQL.

The system provides basic reservation and cancellation functionality with database connectivity and input validation.

---

## 🛠️ Technologies Used

- **Programming Language:** Java
- **GUI:** Java Swing
- **Database:** MySQL 9.7.0
- **Database Connectivity:** JDBC
- **IDE:** Eclipse
- **Version Control:** Git & GitHub

---

## ✨ Features

### 🔐 Login Module

- Username and password authentication
- Invalid credentials are rejected
- Empty username/password validation
- Database-based user authentication

### 🚆 Reservation Module

- Passenger name input
- Train number input
- Automatic train name fetching based on train number
- Class type selection
- Journey date
- Source station
- Destination station
- Required-field validation
- Numeric train number validation
- Ticket booking
- Automatic PNR generation
- Booking confirmation
- Automatic form clearing after successful booking

### ❌ Cancellation Module

- PNR number input
- Fetch reservation details using PNR
- Display complete booking details
- Confirmation dialog before cancellation
- Delete reservation from database
- Invalid PNR validation

---

## 🗄️ Database Design

The project uses MySQL database named:

```text
reservation_db
Tables
1. users

Stores login credentials.

Column	Type	Description
id	INT	Primary key
username	VARCHAR(50)	Unique username
password	VARCHAR(100)	User password
2. trains

Stores train information.

Column	Type	Description
train_no	INT	Primary key
train_name	VARCHAR(100)	Train name
3. reservations

Stores passenger reservation details.

Column	Type	Description
pnr	BIGINT	Auto-generated primary key
passenger_name	VARCHAR(100)	Passenger name
train_no	INT	Train number
train_name	VARCHAR(100)	Train name
class_type	VARCHAR(20)	Class type
journey_date	DATE	Date of journey
source_station	VARCHAR(50)	Source station
destination_station	VARCHAR(50)	Destination station

The train_no column in the reservations table references the trains table.

📂 Project Structure
OnlineReservationSystem
│
├── src
│   └── com
│       └── oasis
│           ├── dao
│           │   ├── LoginDAO.java
│           │   ├── TrainDAO.java
│           │   └── ReservationDAO.java
│           │
│           ├── db
│           │   └── DBConnection.java
│           │
│           ├── model
│           │   ├── Train.java
│           │   └── Reservation.java
│           │
│           └── ui
│               └── [GUI Classes]
│
├── .gitignore
├── db.properties
└── README.md

Note: db.properties contains local database credentials and is excluded from GitHub using .gitignore.

🔄 Application Flow
Login
  ↓
Reservation Form
  ↓
Enter Train Number
  ↓
Fetch Train Name
  ↓
Enter Passenger Details
  ↓
Book Ticket
  ↓
Generate PNR
  ↓
Booking Confirmation
  ↓
Clear Form
Cancellation Flow
Enter PNR
  ↓
Fetch Booking
  ↓
Display Booking Details
  ↓
Cancel Ticket
  ↓
"Are you sure?" Confirmation
  ↓
Delete Reservation
  ↓
Cancellation Successful
🔒 Security and Database Handling
JDBC PreparedStatement is used for database queries.
Database credentials are stored separately in db.properties.
db.properties is excluded from Git tracking using .gitignore.
SQL queries use parameterized statements to reduce SQL injection risk.
🧪 Validation and Testing

The application was tested for:

Invalid login credentials
Empty login fields
Valid login
Invalid/non-numeric train number
Train name auto-fetch
Empty reservation fields
Successful ticket booking
Automatic PNR generation
Database insertion
Reservation fetching using PNR
Ticket cancellation
Invalid/deleted PNR handling
Automatic form clearing
▶️ How to Run
Prerequisites

Make sure the following are installed:

Java JDK
Eclipse IDE
MySQL Server
MySQL JDBC Driver
Database Setup
Start MySQL Server.
Create the database:
CREATE DATABASE reservation_db;
Create the required tables: users, trains, and reservations.
Insert the required user and train records.
Configure the local database credentials in:
db.properties

Example:

db.url=jdbc:mysql://localhost:3306/reservation_db
db.username=root
db.password=YOUR_PASSWORD
Open the project in Eclipse.
Make sure the MySQL JDBC Driver is available in the project.
Run the application's login/GUI class.
Login using a valid database user.
Use the Reservation and Cancellation modules.

## 📸 Screenshots

### 1. Login Screen
![Login Screen](screenshots/01-login.png)

### 2. Login Validation
![Login Validation](screenshots/02-login-validation.png)

### 3. Reservation Form
![Reservation Form](screenshots/03-reservation-form.png)

### 4. Train Name Auto-Fetch
![Train Name Auto-Fetch](screenshots/04-train-auto-fetch.png)

### 5. Booking Success & PNR Generation
![Booking Success](screenshots/05-booking-success.png)

### 6. Cancellation - PNR Fetch
![PNR Fetch](screenshots/06-cancellation-fetch.png)

### 7. Cancellation Confirmation
![Cancellation Confirmation](screenshots/07-cancellation-confirmation.png)

### 8. Cancellation Successful
![Cancellation Success](screenshots/08-cancellation-success.png)

🎯 Internship Task

Organization: Oasis Infobyte
Track: Java Development
Task: Task 1 – Online Reservation System

👨‍💻 Author

Vishal Gorakh Devkar

B.Sc. Computer Science

Java Full Stack Developer

📄 License

This project was developed for educational and internship purposes as part of the Oasis Infobyte Java Development Internship.

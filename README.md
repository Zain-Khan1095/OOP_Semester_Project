# Pharmacy Management System 
**AJava Swing & MySQL Semester Project**

### Group Details
 CMS ID 023-25-0519 <br>
 Name Zain Khan <br>

CMS ID 023-25-0517 <br>
Name Usaid Ur Rehman <br>

Section B (BS AI)
 

---
##  Purpose & Scope
The **Pharmacy Management System** is designed to digitize inventory management for local pharmacies. It bridges the gap between manual record-keeping and complex enterprise software.

### Key Features:
* **Live MySQL Integration:** Real-time data persistence using a standalone MySQL Server.
* **Inventory Intelligence:** Automated "Low Stock" visual indicators and badge highlighting.
* **Sales Processing:** Professional sale handling with quantity validation and total bill calculation.
* **Executive Dashboard:** Instant high-level metrics including Total Inventory Value and Low Stock Alerts.
* **Clean UI:** A modern, distraction-free minimalist interface built with Java Swing.

---

## 📺 Project Demo (Video)
Click the link below to watch the full project walkthrough and feature demonstration:
[**Watch Demo on YouTube**](https://youtu.be/n99cPgJm4Iw)

---

## 🚀 Installation & Setup

### 1. Prerequisites
* **Java JDK 17** or higher.
* **MySQL Server** (Workbench).
* **MySQL Connector J** (`mysql-connector-j-9.7.0.jar`).

### 2. Database Configuration
1. Open MySQL Workbench.
2. Run the following command to create the schema:
   ```sql
   CREATE DATABASE pharmacy_db;

### 2. Database Configuration
1. Run the following command to compile and run:
   ```
   javac -cp ".;mysql-connector-j-9.7.0.jar" app/*.java db/*.java model/*.java view/*.java
2. Second Step
```
   java -cp ".;mysql-connector-j-9.7.0.jar" app.Main

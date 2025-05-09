# 💼 Employee Management System (Java + MySQL)

This is a **basic Employee Management System** created using **Java**, **MySQL**, and **JDBC**. It allows you to perform essential operations on employee records via a command-line interface.

## ✨ Features

- ➕ Add a new employee  
- ❌ Remove an employee  
- 📈 Promote an employee (update salary)  
- 📋 Display all employees  

## 🧰 Technologies Used

- **Java (JDK 8+)**
- **MySQL Database**
- **JDBC (Java Database Connectivity)**
- **VS Code** (or any IDE of your choice)

## 🗄️ Database Setup

Run the following SQL commands in MySQL:

```sql
CREATE DATABASE emp;

USE emp;

CREATE TABLE employees (
  id VARCHAR(10) PRIMARY KEY,
  name VARCHAR(100),
  position VARCHAR(50),
  salary DOUBLE
);

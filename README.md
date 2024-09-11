# ATM Simulator System

## Overview

The ATM Simulator System is a Java-based application designed to simulate basic ATM functionalities. This project allows users to interact with a virtual ATM to perform various banking operations such as checking balances, changing PINs, and making cash withdrawals. The system is built using Java Swing for the user interface and integrates with a MySQL database for data management.

## Features

- **Login System**: Secure user authentication using PIN.
- **Balance Enquiry**: Check current account balance.
- **Change PIN**: Update user PIN with verification.
- **Fast Cash**: Quickly withdraw predefined amounts of cash.

## Requirements

- Java Development Kit (JDK) 8 or higher
- MySQL Database
- JDBC Driver for MySQL

## Setup and Installation

### 1. Clone the Repository

```bash
git clone https://github.com/yourusername/ATM-Simulator-System.git
cd ATM-Simulator-System

2. Configure the Database
Create a MySQL database and tables. You can use the provided SQL scripts (if any) to set up the database schema.
Update the Conn class in your project to include your MySQL database connection details.
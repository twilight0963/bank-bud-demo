- A Java Bank App made using Object-Oriented Programming, Each account is handled using an object, and those are handled using the Manager class.

- Swing Framework is used for the UI, some classes have been made for UI elements that were used multiple times like buttons and dialogue box


- Setting up the database!

1. Download MySQL -  https://dev.mysql.com/downloads/
2. Log in using mysql -u root -p and enter password.
3. Creating the db:
create database {your db name};
use {your db name};
4. Creating tables:
CREATE TABLE Accounts (
    AccountID INT PRIMARY KEY AUTO_INCREMENT,
    owner VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    Balance DECIMAL(10,2) DEFAULT 0.00
);
CREATE TABLE Transactions (
    Type Varchar(10),
    TransactionID INT PRIMARY KEY AUTO_INCREMENT,
    SenderID INT,
    ReceiverID INT,
    Amt DECIMAL(10,2) NOT NULL,
    DateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);
5. Go to DatabaseManager.java, and edit the DB info.


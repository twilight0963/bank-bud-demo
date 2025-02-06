package org.bankbud.mainapp.Classes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String URL = org.bankbud.mainapp.Constants.Constants.DB_URL; // Replace with your DB name
    private static final String USER = org.bankbud.mainapp.Constants.Constants.DB_USER; // Replace with your DB username
    private static final String PASSWORD = org.bankbud.mainapp.Constants.Constants.DB_PASSWORD; // Replace with your DB password

    public static final String ACCTABLE = "";

    private Connection connection;

    // Constructor: Establish connection
    public DatabaseManager() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database successfully!");
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
        }
    }

    // Execute SQL statements (INSERT, UPDATE, DELETE, etc.)
    public boolean execute(String sql) {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
            return true;
        } catch (SQLException e) {
            System.err.println("SQL Execution Failed: " + e.getMessage());
            return false;
        }
    }

    public ResultSet selectQuery(String sql) {
        try (Statement statement = connection.createStatement()) {
            ResultSet result = statement.executeQuery(sql);
            return result;
        } catch (SQLException e) {
            System.err.println("SQL Execution Failed: " + e.getMessage());
            return null;
        }
    }



}

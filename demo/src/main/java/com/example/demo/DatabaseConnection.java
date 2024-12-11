package com.example.demo;

import java.sql.*;

public class DatabaseConnection {

    private static final String url = "jdbc:sqlite:projectdb.db";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Connection error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}

package com.example.demo;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class AttendenceDatabase {
    public void createTables() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Students (
                student_id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_name TEXT NOT NULL\s
                         );
        \s""";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Student table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }

        String sql2 = """
            CREATE TABLE IF NOT EXISTS Enrollments (
                enrollment_id INTEGER PRIMARY KEY \s
                         );
        \s""";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql2);
            System.out.println("Student table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }
    }
}

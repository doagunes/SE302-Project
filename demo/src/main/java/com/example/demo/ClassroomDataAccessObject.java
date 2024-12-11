package com.example.demo;

import java.sql.*;
import java.util.ArrayList;

public class ClassroomDataAccessObject {

    public void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Classroom (
                Classroom TEXT NOT NULL UNIQUE ,
                Capacity INTEGER NOT NULL \s
            );
       \s""";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Classroom table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }
    }

    public void addClassroom(ArrayList<Classroom> classrooms) {
        String sql = "INSERT INTO Classroom (Classroom, Capacity) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (Classroom classroom : classrooms) {
                pstmt.setString(1, classroom.getClassroomName());
                pstmt.setInt(2, classroom.getCapacity());
                pstmt.executeUpdate();

            }
        } catch (SQLException e) {
            System.out.println("Adding error: " + e.getMessage());
        }
    }

    public void getClassrooms() {
        String sql = "SELECT * FROM Classroom";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                System.out.println("Classroom: " + rs.getString("Classroom") +
                        ", Capacity: " + rs.getInt("Capacity"));
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
    }
}

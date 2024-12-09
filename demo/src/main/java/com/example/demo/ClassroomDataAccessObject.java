package com.example.demo;

import java.sql.*;

public class ClassroomDataAccessObject {

    public void addClassroom(String classroom, int capacity){
        String sql = "INSERT INTO Classroom (Classroom, Capacity) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, classroom);
            pstmt.setInt(2, capacity);
            pstmt.executeUpdate();
            System.out.println("Classroom added: " + classroom);
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
                System.out.println("Classroom: " + rs.getString("Classroom") + ", Capacity: " + rs.getInt("Capacity"));
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
    }
}

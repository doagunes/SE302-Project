package com.example.demo;

import java.sql.*;
import java.util.ArrayList;

public class ClassroomDataAccessObject {


    public void createTable() {

    /*
        String sql2 = "DROP TABLE IF EXISTS Classroom";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql2);
            System.out.println("Classroom table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }

     */

        String sql = """
            CREATE TABLE IF NOT EXISTS Classroom (
                
                Classroom TEXT NOT NULL ,
                Capacity INTEGER NOT NULL ,
                UNIQUE (Classroom)  \s           
               
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
        String sql = "INSERT OR IGNORE INTO Classroom (Classroom, Capacity) VALUES (?, ?)";
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

    public ArrayList<Classroom> getClassrooms() {
        ArrayList<Classroom> allClassroom = new ArrayList<>();
        String sql = "SELECT * FROM Classroom";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {

                allClassroom.add(new Classroom(rs.getString("Classroom"), rs.getInt("Capacity")));

            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return  allClassroom;
    }


    public int getCapacityWhereClassroomIs(String classroomName) {
        int capacity = 0;
        String sql = "SELECT Capacity FROM Classroom WHERE Classroom = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, classroomName);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasResult = false;

                while (rs.next()) { // it is checking for hasResult
                    hasResult = true;
                    capacity = rs.getInt("Capacity");

                }

                if (!hasResult) {
                    System.out.println("No capacity found for Classroom: " + classroomName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return capacity;
    }

}

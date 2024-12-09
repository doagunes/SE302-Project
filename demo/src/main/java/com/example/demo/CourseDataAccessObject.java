package com.example.demo;

import java.sql.*;

public class CourseDataAccessObject {

    private void createTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS Course (
                              Course TEXT NOT NULL,
                              TimeToStart TEXT NOT NULL,
                              DurationInLectureHours INTEGER NOT NULL,
                              Lecturer TEXT NOT NULL,
                              Students TEXT NOT NULL
                          );
        """;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Course table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }
    }

    public void addCourse(String course, String timeToStart, int duration, String lecturer, String students){

        createTable();
        String sql = "INSERT INTO Course(Course, TimeToStart, DurationInLectureHours, Lecturer, Students) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            pstmt.setString(1, course);
            pstmt.setString(2, timeToStart);
            pstmt.setInt(3, duration);
            pstmt.setString(4, lecturer);
            pstmt.setString(5, students);
            pstmt.executeUpdate();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getCourses(){
        String sql = "SELECT * FROM Course";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                System.out.println("Course: " + rs.getString("Course") +
                        ", TimeToStart: " + rs.getString("TimeToStart") +
                        ", DurationInLectureHours: " + rs.getInt("DurationInLectureHours") +
                        ", Lecturer: " + rs.getString("Lecturer") +
                        ", Students: " + rs.getString("Students"));
            }

        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
    }
}

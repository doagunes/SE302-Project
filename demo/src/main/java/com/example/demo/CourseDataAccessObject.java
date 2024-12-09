package com.example.demo;

import java.sql.*;
import java.util.ArrayList;

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

    public void addCourse(ArrayList<Course> courses){

        createTable();
        String sql = "INSERT INTO Course(Course, TimeToStart, DurationInLectureHours, Lecturer, Students) VALUES (?, ?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            for(Course course : courses){
                pstmt.setString(1, course.getCourseID());
                pstmt.setString(2, course.getTimeToStart());
                pstmt.setInt(3, course.getDuration());
                pstmt.setString(4, course.getLecturerName());
                String studentsAsString = String.join(",", course.getStudentNames());
                pstmt.setString(5, studentsAsString);
                pstmt.executeUpdate();
            }

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

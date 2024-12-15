package com.example.demo;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

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
            CREATE TABLE IF NOT EXISTS Attendance (
                attendance_id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_id INTEGER NOT NULL,
                student_name TEXT NOT NULL,
                course_name TEXT NOT NULL,
                absence_count INTEGER DEFAULT 0,
                FOREIGN KEY (student_name) REFERENCES Students(student_name),
                FOREIGN KEY (student_id) REFERENCES Students(student_id),
                FOREIGN KEY (course_name) REFERENCES Course(Course),
                UNIQUE(student_id, course_name) -- Aynı öğrenci-kurs ilişkisi tekrar olmasın
                 \s
                         );
        \s""";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql2);
            System.out.println("Attendance table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }
    }
    public void addStudentsFromCSV() throws IOException {
        ArrayList<String> allStudents = CSV_Reader.readAllStudents();
        String sql = "INSERT OR IGNORE INTO Students(student_name) VALUES (?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)){
            for(String student : allStudents){
                pstmt.setString(1, student);
                pstmt.executeUpdate();        // Sorgu çalıştırılır

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void addAttendancesWithInitialDatas() {
        String courseNamesQuery = "SELECT Course, Students FROM COURSE";
        Map<String, ArrayList<String>> courseAndStudentsMap = new HashMap<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(courseNamesQuery);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ArrayList<String> studentsOfCurrentLine = stringToArrayList(rs.getString("Students"));
                courseAndStudentsMap.put(rs.getString("Course"), studentsOfCurrentLine);
            }

        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }

        String insertQuery = "INSERT OR IGNORE INTO Attendance(student_id, student_name, course_name, absence_count) VALUES (?, ?, ?, ?)";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertQuery)){
            /*
            for(String student : allStudents){
                pstmt.setString(1, student);
                pstmt.executeUpdate();        // Sorgu çalıştırılır

            }
            */

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ArrayList<String> stringToArrayList(String students) {
        ArrayList<String> studentList = new ArrayList<>();
        if (students != null && !students.isEmpty()) {
            // Virgülle ayrılmış String'i parçalayarak bir ArrayList'e çevir
            studentList.addAll(Arrays.asList(students.split(",")));
        }
        return studentList;
    }

    public ArrayList<String> studentsOfSpecificCourse (Course course) {
        ArrayList<String> studentsOfCourse = new ArrayList<>();

        // SQL sorgusu
        String query = "SELECT Students FROM COURSE WHERE Course = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            // Parametre olarak courseName'i ayarla
            pstmt.setString(1, course.getCourseID());

            // Sorguyu çalıştır ve sonuçları al
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // "Students" sütunundaki virgülle ayrılmış isimleri al
                String studentsString = rs.getString("Students");

                // İsimleri ArrayList'e dönüştür ve döndür
                studentsOfCourse = stringToArrayList(studentsString);
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        return studentsOfCourse;
    }

}

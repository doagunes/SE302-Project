package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CourseDataAccessObject {

    public void createTable() {

        /*
        String sql2 = "DROP TABLE IF EXISTS Course";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql2);
            System.out.println("Classroom table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }

         */


        String sql = """
            CREATE TABLE IF NOT EXISTS Course (
                              Course TEXT NOT NULL  ,
                              TimeToStart TEXT NOT NULL,
                              DurationInLectureHours INTEGER NOT NULL,
                              Lecturer TEXT NOT NULL,
                              Students TEXT NOT NULL,
                               UNIQUE(Course) \s
                         );
        \s""";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Course table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }
    }

    //TODO user yeni bir kurs ekleyeceği zaman addNewCourse methodu lazım
    public void addCourse(ArrayList<Course> courses){ //csv'deki verileri kullanıyor


        String sql = "INSERT OR IGNORE INTO Course(Course, TimeToStart, DurationInLectureHours, Lecturer, Students) VALUES (?, ?, ?, ?, ?)";
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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Course> getCourses(){
        ArrayList<Course> AllCourses = new ArrayList<>();
        String sql = "SELECT * FROM Course";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                ArrayList<String> studentsInCourse = new ArrayList<>();
                String studentsString = rs.getString("Students");
                studentsInCourse = stringToArrayList(studentsString);
                AllCourses.add(new Course(rs.getString("Course"), rs.getString("TimeToStart"),
                        rs.getInt("DurationInLectureHours"), rs.getString("Lecturer"), studentsInCourse));

                /*
                System.out.println("Course: " + rs.getString("Course") +
                        ", TimeToStart: " + rs.getString("TimeToStart") +
                        ", DurationInLectureHours: " + rs.getInt("DurationInLectureHours") +
                        ", Lecturer: " + rs.getString("Lecturer") +
                        ", Students: " + rs.getString("Students"));

                 */
            }

        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return AllCourses;
    }
    // Helper method to convert a comma-separated String to an ArrayList
    private ArrayList<String> stringToArrayList(String students) {
        ArrayList<String> studentList = new ArrayList<>();
        if (students != null && !students.isEmpty()) {
            // Virgülle ayrılmış String'i parçalayarak bir ArrayList'e çevir
            studentList.addAll(Arrays.asList(students.split(",")));
        }
        return studentList;
    }

    //TODO Öğretmen ve öğrenciye göre kursların çekidleiği methodun return parametesi Course olkucak!
    public ArrayList<String> getCourseWhereLecturerIs(String lecturerName) {
        ArrayList<String> courseIDs = new ArrayList<>();
        String sql = "SELECT Course FROM Course WHERE Lecturer = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, lecturerName);

            try (ResultSet rs = pstmt.executeQuery()) {
                boolean hasResults = false;
                while (rs.next()) {
                    hasResults = true;
                    courseIDs.add(rs.getString("Course"));

                }
                if (!hasResults) {
                    System.out.println("No courses found for Lecturer: " + lecturerName);
                }
            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return courseIDs;
    }
    public ArrayList<String> getCoursesBasedOnStudent (String studentName) {
        ArrayList<String> courseIDs = new ArrayList<>();
        String sql = "SELECT Course FROM Course WHERE Students LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // '%studentName%' ifadesini SQL sorgusuna ekler.
            pstmt.setString(1, "%" + studentName + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    courseIDs.add(rs.getString("Course"));
                }

            }
        } catch (SQLException e) {
            System.out.println("Query error: " + e.getMessage());
        }
        return courseIDs;
        //Helper method ile bu Course ID'leri Course objesine dönüştür arrayListin içinde.
    }


}

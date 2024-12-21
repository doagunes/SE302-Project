package com.example.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class AssignCourseClassroomDB {
    public static void createTables() {

        //TODO Duplicate öğrenci probblem var - SOLVED
        String sql = """
            CREATE TABLE IF NOT EXISTS Assign (
                course_id TEXT NOT NULL ,
                classroom_name TEXT NOT NULL,
                FOREIGN KEY (course_id) REFERENCES Course(Course),
                FOREIGN KEY (classroom_name) REFERENCES Classroom(Classroom),
                UNIQUE (course_id, classroom_name)                               \s
                         );
        \s""";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Assign table has just created or it already exists.");
        } catch (SQLException e) {
            System.out.println("Creating table error: " + e.getMessage());
        }

    }

    public static void initializeAssigning (Course course, Classroom classroom) {
        String courseName = course.getCourseID();
        String classroomName = classroom.getClassroomName();

        String insertQuery = "INSERT OR IGNORE INTO Assign(course_id, classroom_name) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, courseName);
            preparedStatement.setString(2, classroomName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Insertion error: " + e.getMessage(), e);
        }
    }
    public static ArrayList<Course> getCourseNamesByClassroom(Classroom classroom) {
        ArrayList<String> courseList = new ArrayList<>();

        // SQL sorgusu
        String query = "SELECT course_id FROM Assign WHERE classroom_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            // Parametreyi ayarla
            preparedStatement.setString(1, classroom.getClassroomName());

            // Sorguyu çalıştır ve sonuçları işle
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                courseList.add(resultSet.getString(1));
            }

        } catch (SQLException e) {
            System.out.println("An error occurred while fetching courses: " + e.getMessage());
        }

        return getCourseByClassroom(courseList);
    }

    public static ArrayList<Course> getCourseByClassroom(ArrayList<String> courseNames) {
        ArrayList<Course> courseList = new ArrayList<>();



        for (String courseName : courseNames) {
            // SQL sorgusu
            String query = "SELECT * FROM Course WHERE Course = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = conn.prepareStatement(query)) {

                // Parametreyi ayarla
                preparedStatement.setString(1, courseName);

                // Sorguyu çalıştır ve sonuçları işle
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    courseList.add(new Course(resultSet.getString(1), resultSet.getString(2),
                            resultSet.getInt(3), resultSet.getString(4),
                            stringToArrayList(resultSet.getString(5))));
                }

            } catch (SQLException e) {
                System.out.println("An error occurred while fetching courses: " + e.getMessage());
            }
        }

        return courseList;
    }
    private static ArrayList<String> stringToArrayList(String students) {
        ArrayList<String> studentList = new ArrayList<>();
        if (students != null && !students.isEmpty()) {
            // Virgülle ayrılmış String'i parçalayarak bir ArrayList'e çevir
            studentList.addAll(Arrays.asList(students.split(",")));
        }
        return studentList;
    }

}

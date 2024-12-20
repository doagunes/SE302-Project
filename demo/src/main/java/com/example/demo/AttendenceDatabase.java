package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AttendenceDatabase {
    public static void dropTables() {
        String sql1 = "DROP TABLE IF EXISTS Students";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql1);
            System.out.println("Student table has just deleted or it already not exists.");
        } catch (SQLException e) {
            System.out.println("Deleting table error: " + e.getMessage());
        }

        String sql2 = "DROP TABLE IF EXISTS Attendance";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql2);
            System.out.println("Attendance table has just deleted or it already not exists.");
        } catch (SQLException e) {
            System.out.println("Deleting table error: " + e.getMessage());
        }
    }

    public static void createTables() {

        //TODO Duplicate öğrenci probblem var - SOLVED
        String sql = """
            CREATE TABLE IF NOT EXISTS Students (
                student_id INTEGER PRIMARY KEY AUTOINCREMENT,
                student_name TEXT NOT NULL,
                UNIQUE (student_name)                               \s
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
                FOREIGN KEY (course_name) REFERENCES Course(Course)
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

    public static void addStudentsFromCSV() throws IOException {
        ArrayList<String> allStudents = CSV_Reader.readAllStudents();
        String sql = "INSERT INTO Students(student_name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (String student : allStudents) {
                pstmt.setString(1, student);
                pstmt.executeUpdate();        // Sorgu çalıştırılır

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void addAttendancesWithInitialDatas() {
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

        //INFO Map'e doğru ekleniyor

        //Attendance ekleme sorgusu
        String insertQuery = "INSERT INTO Attendance(student_id, student_name, course_name, absence_count) VALUES (?, ?, ?, ?)";
        String getStudentQuery = "SELECT student_id, student_name FROM Students WHERE student_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
             PreparedStatement getStudentStmt = conn.prepareStatement(getStudentQuery)) {

            // Ders ve öğrenciler eşleştirmesini işle
            for (Map.Entry<String, ArrayList<String>> entry : courseAndStudentsMap.entrySet()) {
                String courseName = entry.getKey();            // Ders adı
                // Course name'de se115 dönüyor burda sıkıntı yok
                ArrayList<String> students = entry.getValue(); // O derse kayıtlı öğrenciler

                for (String student : students) {
                    // Öğrencinin ID'sini ve adını al
                    getStudentStmt.setString(1, student);
                    ResultSet rs = getStudentStmt.executeQuery();
                    if (rs.next()) {
                        int studentId = rs.getInt("student_id");
                        String studentName = rs.getString("student_name");

                        // Attendance kaydı ekle
                        insertStmt.setInt(1, studentId);
                        insertStmt.setString(2, studentName);
                        insertStmt.setString(3, courseName);
                        insertStmt.setInt(4, 0); // Başlangıç devamsızlık sayısı
                        insertStmt.executeUpdate();
                    } else {
                        System.out.println("Öğrenci bulunamadı: " + student);
                    }
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Insertion error: " + e.getMessage(), e);
        }
    }

    private static ArrayList<String> stringToArrayList(String students) {
        ArrayList<String> studentList = new ArrayList<>();
        if (students != null && !students.isEmpty()) {
            // Virgülle ayrılmış String'i parçalayarak bir ArrayList'e çevir
            studentList.addAll(Arrays.asList(students.split(",")));
        }
        return studentList;
    }

    public static ArrayList<String> studentsOfSpecificCourse(Course course) {
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

    public static ObservableList<AttendanceStudents> getAttendanceStudents() {
        ObservableList<AttendanceStudents> attendance_students = FXCollections.observableArrayList();
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Attendance")) {

            while (rs.next()) {
                attendance_students.add(new AttendanceStudents(
                        rs.getInt("attendance_id"),
                        rs.getInt("student_id"),
                        rs.getString("student_name"),
                        rs.getString("course_name"),
                        rs.getInt("absence_count")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return attendance_students;
    }

    public static int getAbsenceCount(Course course, Student student) throws SQLException {
        int absenceCount = 0;
        String studentName = student.getName();
        String courseName = course.getCourseID();

        String query = "SELECT absence_count FROM Attendance WHERE course_name = ? AND student_name = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, courseName);
            pstmt.setString(2, studentName);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                absenceCount = rs.getInt("absence_count");
            }


        }
        return absenceCount;
    }
    public static void incrementAbsenceCount (AttendanceStudents attendanceStudents) {
        String studentName = attendanceStudents.getStudentName();
        String courseName = attendanceStudents.getCourseName();
        System.out.println(studentName);
        System.out.println(courseName);
        try (Connection conn = DatabaseConnection.getConnection()) {

            // absence_count'u 1 artıran sorgu
            String updateQuery = "UPDATE Attendance " +
                    "SET absence_count = absence_count + 1 " +
                    "WHERE student_name = ? AND course_name = ?";

            PreparedStatement preparedStatement = conn.prepareStatement(updateQuery);

            // Parametreleri ayarla
            preparedStatement.setString(1, studentName);
            preparedStatement.setString(2, courseName);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}



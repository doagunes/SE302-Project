package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;

import java.util.ArrayList;

public class StudentManagementController {

    @FXML
    private Label studentNameLabel; // Label to display student name

    public static Student student; // Instance variable to hold the student object

    @FXML
    private TableView<Course> courseTableView;

    @FXML
    private TableColumn<Course, String> CoursesColumn;

    @FXML
    private Button removeStudentButton; // Button to trigger adding student to course

    @FXML
    public void initialize() {
        // Initialize the TableColumn to display course IDs
        CoursesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseID()));

        // Ensure that the student object is set before calling any methods that depend on it
        if (student != null) {
            System.out.println(student);
            loadCourses();
        }
        if (student == null) {
            System.out.println("student NULL");
        }

        // Set the button action to call the method when clicked
        removeStudentButton.setOnAction(event -> handleRemoveStudentFromCourse());
    }

    private void loadCourses() {
        // Fetch courses based on the student
        ArrayList<Course> courses = fetchCoursesFromDatabase();

        // Add courses to TableView
        ObservableList<Course> coursesData = FXCollections.observableArrayList(courses);
        courseTableView.setItems(coursesData);
    }

    private ArrayList<Course> fetchCoursesFromDatabase() {
        // Fetch courses based on the student name
        CourseDataAccessObject courseDAO = new CourseDataAccessObject();
        ArrayList<Course> courses = courseDAO.getCoursesBasedOnStudent(student.getName());
        return courses;
    }

    // Method to initialize the controller with the selected student
    public void setStudent(Student student) {
        this.student = student;
        // Set the student name on the label
        studentNameLabel.setText(student.getName());

        // Now load the courses for this student
        loadCourses();
    }

    // Method to add the student to a selected course
    @FXML
    private void handleRemoveStudentFromCourse() {
        // Admin sınıfını başlat
        Admin admin = new Admin();

        // Öğrencinin CourseTableView'inden seçili kursu alıyoruz
        Course selectedCourse = courseTableView.getSelectionModel().getSelectedItem();
        System.out.println(selectedCourse + selectedCourse.getCourseID());
        // Seçili kursu ve öğrenci objesini kontrol ediyoruz
        if (selectedCourse != null && student != null) {
            admin.removeStudentFromCourse(selectedCourse, student); // Admin sınıfındaki metodu çağırıyoruz
            System.out.println("Student removed from the course!");
        } else {
            System.out.println("Please select a course and make sure the student is selected.");
        }
    }
}

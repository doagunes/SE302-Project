package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class TransferScreenController {

    public static Student student; // Instance variable to hold the student object
    public StudentManagementController studentManagementController;

    @FXML
    private TableView<Course> courseTableView;

    @FXML
    private TableColumn<Course, String> CoursesColumn;

    private Course selectedCourse1;

    @FXML
    private TableView<Course> tableView;
    @FXML
    private TableColumn<Course, String> courseID;
    @FXML
    private TableColumn<Course, String> timeToStart;
    @FXML
    private TableColumn<Course, String> duration;
    @FXML
    private TableColumn<Course, String> lecturerName;

    private ObservableList<Course> allCourses;

    private Course selectedCourse2;

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

        // Initialize the TableColumn to display course IDs
        CoursesColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCourseID()));

        // Sütunları yapılandır
        courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        timeToStart.setCellValueFactory(new PropertyValueFactory<>("timeToStart"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        lecturerName.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));

        // Verileri yükle
        allCourses = CourseDataAccessObject.getCoursesWithoutStudents();
        tableView.setItems(allCourses);

        courseTableView.setOnMouseClicked(this::handleCourseSelection);  // Course selection handler
        tableView.setOnMouseClicked(this::handleCourseSelection2);  // Course selection handler
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

    public Course getSelectedCourse() {
        return courseTableView.getSelectionModel().getSelectedItem();
    }


    // Set the student object for this controller
    public void setStudent(Student student) {
        this.student = student;  // Assign the student to the class variable
        System.out.println("Student set: " + student.getName());
        loadCourses();  // Reload courses based on the student
    }

    // Event handler for when a course is clicked
    @FXML
    private void handleCourseSelection(MouseEvent event) {
        selectedCourse1 = courseTableView.getSelectionModel().getSelectedItem();
        if (selectedCourse1 != null) {
            System.out.println("Selected course 1: " + selectedCourse1.getCourseID());
        }
    }

    // Event handler for when a course is clicked
    @FXML
    private void handleCourseSelection2(MouseEvent event) {
        // Get the selected course from the table
        selectedCourse2 = tableView.getSelectionModel().getSelectedItem();

        // Pass the selected course to StudentManagementController and close the window
        if (selectedCourse2 != null) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentManagement.fxml"));
                Parent root = loader.load();  // Ensure the controller is loaded



                StudentManagementController studentManagementController = loader.getController();

                studentManagementController.handleTransferCourse(selectedCourse1, selectedCourse2);

                Stage stage = (Stage) courseTableView.getScene().getWindow();
                stage.close();
            } catch (IOException e) {
                e.printStackTrace();  // Handle any exceptions that might occur during the loading of the FXML
            }
        }
    }

    @FXML
    public void setStudentManagamentController (StudentManagementController studentManagementController) {
        this.studentManagementController = studentManagementController;
    }

}

package com.example.demo;

import eu.hansolo.fx.heatmap.OpacityDistribution;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    private Button addStudentButton;

    @FXML
    private Button removeStudentButton; // Button to trigger adding student to course

    @FXML
    private Button transferStudentButton;

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

        addStudentButton.setOnAction(event -> OpenViewCoursesAdd());

        removeStudentButton.setOnAction(event -> handleRemoveStudentFromCourse());

        transferStudentButton.setOnAction(event -> OpenViewCoursesTransfer());

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
        studentNameLabel.setText(student.getName());

        // Now load the courses for this student
        loadCourses();
    }

    @FXML
    private void OpenViewCoursesAdd() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewCourses.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Pass the action type to the ViewCoursesController
        ViewCoursesController controller = fxmlLoader.getController();

        // Create a new stage and show the FXML
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select a Course Please!");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }

    @FXML
    private void OpenViewCoursesTransfer() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TransferScreen.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Pass the action type to the ViewCoursesController
        TransferScreenController controller = fxmlLoader.getController();
        controller.setStudent(student);
        controller.setStudentManagamentController(this);

        // Create a new stage and show the FXML
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select two Courses Please!");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }


    // Method to handle adding the selected course to the student called from ViewCoursesController after selecting a course
    public void handleAddToCourse(Course selectedCourse) {
        if (selectedCourse != null && student != null) {
            // Use the Admin class to add the student to the selected course
            Admin admin = new Admin();
            admin.addStudentToCourse(selectedCourse, student);

            // Reload the courses to reflect the changes in the TableView
            loadCourses();
            System.out.println("Student added to the course!");
        } else {
            System.out.println("Please select a course and ensure a student is selected.");
        }
    }

    @FXML
    private void handleRemoveStudentFromCourse() {
        // Open the CourseTableView page
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CourseTableView.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Get the CourseTableViewController instance and pass the student
        CourseTableViewController courseTableController = fxmlLoader.getController();
        courseTableController.setStudent(student);  // Pass the student to the controller

        // Create and show the new stage with the CourseTableView
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Select a Course to Remove");
        stage.setScene(new Scene(root));
        stage.showAndWait();

        // Get the selected course from the CourseTableViewController
        Course selectedCourse = courseTableController.getSelectedCourse();

        if (selectedCourse != null && student != null) {
            // Admin class instance to manage course enrollment
            Admin admin = new Admin();

            // Remove the student from the selected course
            admin.removeStudentFromCourse(selectedCourse, student);

            // Reload the courses to reflect the changes in the TableView
            loadCourses();
            System.out.println("Student removed from the course!");
        } else {
            System.out.println("Please select a course and ensure a student is selected.");
        }
    }

    public void handleTransferCourse(Course selectedCourse1, Course selectedCourse2) {
        if (selectedCourse1 != null && selectedCourse2 != null && student != null) {
            // Use the Admin class to add the student to the selected course
            Admin admin = new Admin();
            admin.transferStudentToAnotherCourse(selectedCourse1, selectedCourse2, student);

            // Reload the courses to reflect the changes in the TableView
            loadCourses();
            System.out.println("Student made the transfer!");
        } else {
            System.out.println("Please courses and ensure a student is selected.");
        }
    }

}

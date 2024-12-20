package com.example.demo;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class StudentsController {

    @FXML
    private TableView<Student> studentTableView;

    @FXML
    private TableColumn<Student, String> studentNameColumn;

    private Student selectedStudent;  // Instance variable to hold the selected student

    public void initialize() {
        studentNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        // Fetch students from database
        ArrayList<Student> students = fetchStudentsFromDatabase();

        // Add students to TableView
        ObservableList<Student> studentData = FXCollections.observableArrayList(students);
        studentTableView.setItems(studentData);

        // Add listener to handle row selection
        studentTableView.setOnMouseClicked(this::handleStudentSelection);
    }

    private ArrayList<Student> fetchStudentsFromDatabase() {
        // Fetch all students from the database using the CourseDataAccessObject
        ArrayList<String> studentNames = CourseDataAccessObject.getAllStudents();

        // Convert the list of student names into Student objects
        ArrayList<Student> students = new ArrayList<>();
        for (String studentName : studentNames) {
            students.add(new Student(studentName)); // Create Student object for each student
        }

        return students;
    }

    // Event handler for when a student is clicked
    private void handleStudentSelection(MouseEvent event) {
        // Get the selected student from the table
        selectedStudent = studentTableView.getSelectionModel().getSelectedItem();
        selectedStudent.getCourses().addAll(CourseDataAccessObject.getCoursesBasedOnStudent(selectedStudent.getName()));
        System.out.println(selectedStudent.getCourses().get(1));
        StudentManagementController.student = selectedStudent;

        // If a student is selected, open the StudentManagement.fxml
        if (selectedStudent != null) {
            try {
                // Load the StudentManagement.fxml file
                FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentManagement.fxml"));
                Parent root = loader.load();

                // Get the controller of the StudentManagement.fxml
                StudentManagementController controller = loader.getController();
                controller.setStudent(selectedStudent); // Pass the selected student to the controller

                // Create a new stage (window)
                Stage stage = new Stage();
                stage.setTitle("Student Management - " + selectedStudent.getName()); // Set title to include student name
                stage.initModality(Modality.WINDOW_MODAL); // Optional: makes it modal, so the user cannot interact with the main window
                stage.setScene(new Scene(root));

                // Show the new stage
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
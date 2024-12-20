package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewCoursesController {

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
    @FXML
    private TextField searchField; // Arama kutusu
    @FXML
    private Button searchButton;  // Arama butonu
    @FXML
    private Button resetButton;   // Reset butonu

    private ObservableList<Course> allCourses;

    public void initialize() {
        // Sütunları yapılandır
        courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        timeToStart.setCellValueFactory(new PropertyValueFactory<>("timeToStart"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        lecturerName.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));

        // Verileri yükle
        allCourses = CourseDataAccessObject.getCoursesWithoutStudents();
        tableView.setItems(allCourses);

        // Arama butonuna işlem bağla
        searchButton.setOnAction(event -> searchCourse());

        // Reset butonuna işlem bağla
        resetButton.setOnAction(event -> resetTable());
    }

    private void searchCourse() {
        String searchQuery = searchField.getText();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            tableView.setItems(allCourses); // Eğer arama kutusu boşsa tüm kursları göster
            return;
        }

        ObservableList<Course> filteredCourses = FXCollections.observableArrayList();

        for (Course course : allCourses) {
            if (course.getCourseID().equalsIgnoreCase(searchQuery)) {
                filteredCourses.add(course);
            }
        }

        tableView.setItems(filteredCourses);
    }

    private void resetTable() {
        searchField.clear(); // Arama kutusunu temizle
        tableView.setItems(allCourses); // Tüm kursları tekrar yükle
    }
}

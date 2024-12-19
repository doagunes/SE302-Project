package com.example.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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


    public void initialize() {
        // Sütunları yapılandır
        courseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        timeToStart.setCellValueFactory(new PropertyValueFactory<>("timeToStart"));
        duration.setCellValueFactory(new PropertyValueFactory<>("duration"));
        lecturerName.setCellValueFactory(new PropertyValueFactory<>("lecturerName"));

        // Verileri yükle
        tableView.setItems(CourseDataAccessObject.getCoursesWithoutStudents());
    }


}
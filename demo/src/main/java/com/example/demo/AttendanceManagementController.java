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
public class AttendanceManagementController {

        @FXML
        private TableView<AttendanceStudents> tableView;
        @FXML
        private TableColumn<Student, String> idColumn;
        @FXML
        private TableColumn<Student, String> studentIdColumn;
        @FXML
        private TableColumn<Student, String> studentNameColumn;
        @FXML
        private TableColumn<Student, String> courseNameColumn;
        @FXML
        private TableColumn<Student, String> absenceCountColumn;

        public void initialize() {
            // Sütunları yapılandır
            idColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
            studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            absenceCountColumn.setCellValueFactory(new PropertyValueFactory<>("absenceCount"));

            // Verileri yükle
            tableView.setItems(AttendenceDatabase.getAttendanceStudents());
        }


}

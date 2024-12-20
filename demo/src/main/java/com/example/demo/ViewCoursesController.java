package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.*;
import java.util.ArrayList;

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
    private TableColumn<Course, Void> viewStudentsColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button resetButton;

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

        // "View Students" sütununu ekle
        addViewStudentsButton();

        // Arama butonuna işlem bağla
        searchButton.setOnAction(event -> searchCourse());

        // Reset butonuna işlem bağla
        resetButton.setOnAction(event -> resetTable());
    }

    private void searchCourse() {
        String searchQuery = searchField.getText();
        if (searchQuery == null || searchQuery.trim().isEmpty()) {
            tableView.setItems(allCourses); // Eğer arama kutusu boşsa tüm kursları göster
        } else {
            ObservableList<Course> filteredCourses = FXCollections.observableArrayList();
            for (Course course : allCourses) {
                if (course.getCourseID().equalsIgnoreCase(searchQuery)) {
                    filteredCourses.add(course);
                }
            }
            tableView.setItems(filteredCourses); // Filtrelenmiş kursları göster
        }
        addViewStudentsButton(); // "View Students" butonlarını yeniden ekle
    }

    private void resetTable() {
        searchField.clear(); // Arama kutusunu temizle
        tableView.setItems(allCourses); // Tüm kursları tekrar yükle
        addViewStudentsButton(); // "View Students" butonlarını tekrar ekle
    }


    private void addViewStudentsButton() {
        Callback<TableColumn<Course, Void>, TableCell<Course, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Course, Void> call(final TableColumn<Course, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("View Students");

                    {
                        btn.setOnAction(event -> {
                            Course selectedCourse = getTableView().getItems().get(getIndex());
                            showStudentsForCourse(selectedCourse);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null); // Boş hücrelerde buton gösterme
                        } else {
                            setGraphic(btn); // Hücrede butonu göster
                        }
                    }
                };
            }
        };

        viewStudentsColumn.setCellFactory(cellFactory);
    }

    private void showStudentsForCourse(Course course) {
        ArrayList<String> students = AttendenceDatabase.studentsOfSpecificCourse(course);
        System.out.println("Students in course " + course.getCourseID() + ": " + students);

        // Öğrencileri göstermek için ScrollPane içinde bir TextArea kullan
        StringBuilder studentsList = new StringBuilder();
        for (String student : students) {
            studentsList.append(student).append("\n");
        }

        TextArea studentsTextArea = new TextArea();
        studentsTextArea.setText(studentsList.toString());
        studentsTextArea.setWrapText(true);
        studentsTextArea.setEditable(false);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(studentsTextArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Stage studentsStage = new Stage();
        studentsStage.setTitle("Students in Course: " + course.getCourseID());
        Scene scene = new Scene(scrollPane, 400, 300);
        studentsStage.setScene(scene);
        studentsStage.show();
    }
}

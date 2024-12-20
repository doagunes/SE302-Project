package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.util.ArrayList;

public class ViewLecturersController {

    @FXML
    private TableView<String> lecturerTableView;
    @FXML
    private TableColumn<String, String> lecturerNameColumn;
    @FXML
    private TableColumn<String, Void> viewScheduleColumn;

    private ObservableList<String> allLecturers;

    public void initialize() {
        // Lecturer isimlerini çek
        ArrayList<String> lecturers = CourseDataAccessObject.getLecturers();
        allLecturers = FXCollections.observableArrayList(lecturers);

        // TableView ayarları
        lecturerNameColumn.setCellValueFactory(cellData -> javafx.beans.binding.Bindings.createStringBinding(() -> cellData.getValue()));
        lecturerTableView.setItems(allLecturers);

        // "View Schedule" butonları ekle
        addViewScheduleButton();
    }

    private void addViewScheduleButton() {
        // Butonları eklemek için cellFactory kullanıyoruz
        Callback<TableColumn<String, Void>, TableCell<String, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<String, Void> call(final TableColumn<String, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("View Schedule");

                    {
                        btn.setOnAction(event -> {
                            String selectedLecturer = getTableView().getItems().get(getIndex());
                            showScheduleForLecturer(selectedLecturer);
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

        viewScheduleColumn.setCellFactory(cellFactory);
    }

    private void showScheduleForLecturer(String lecturer) {
        // Burada, seçilen öğretim üyesinin ders programını göstermek için gerekli işlemi yapabilirsiniz
        System.out.println("Showing schedule for " + lecturer);
    }
}

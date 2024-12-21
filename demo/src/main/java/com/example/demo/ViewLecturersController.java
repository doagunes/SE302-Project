package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
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
                            int index = getIndex();
                            String selectedLecturer = getTableView().getItems().get(index);
                            openSchedule(selectedLecturer);

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

    private void openSchedule(String selectedLecturer)  {
        LecturerScheduleController.setLecturerName(selectedLecturer);
        System.out.println(LecturerScheduleController.lecturer);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewLecturerSchedule.fxml"));
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //yeni stage oluştur ve .fxml'i göster
        Stage stage = new Stage();

        stage.setMinWidth(400);
        stage.setMinHeight(400);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Schedule");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
}

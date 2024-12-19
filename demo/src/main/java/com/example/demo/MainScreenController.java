package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainScreenController {

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("CAUTION !!");
        alert.setHeaderText(null);
        alert.setContentText("Add a book to the catalog by pressing the menubar-->Add\n" +
                "Save the current state of the catalog by pressing the menubar-->Save\n" +
                "Export your catalog by pressing the menubar-->Export All\n" +
                "Export the selected book by pressing the menubar-->Export Selected\n" +
                "Import a saved catalog by pressing the menubar-->Open File");
        alert.showAndWait();
    }

    @FXML
    private Button CourseBtn;

    @FXML
    private Button StudentBtn;

    @FXML
    private Button LecturereBtn;

    @FXML
    private Button ClasstroomBtn;

    @FXML
    private Button AttendanceBtn;

    @FXML
    private void startCoursePage () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CourseManagementfxml.fxml"));
        Parent root = fxmlLoader.load();



        //yeni stage oluştur ve .fxml'i göster
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Course Management");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

}

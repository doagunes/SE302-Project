package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

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


}

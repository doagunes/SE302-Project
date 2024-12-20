package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class MainScreenController {

    @FXML
    private void about() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Syllabus App");
        alert.setHeaderText("How to Use the Syllabus App");

        // Create the main content text
        String aboutText = "Welcome to the Syllabus App!\n\n" +
                "Here are the key functionalities:\n" +
                "1. View Weekly Schedule: Check your courses and activities for the week.\n" +
                "2. Add Courses: Add new courses to your schedule using the 'Add Course' option.\n" +
                "3. Remove Courses: Remove courses from your schedule by selecting them.\n" +
                "4. Export Schedule: Save your current schedule as a file for later use.\n" +
                "5. Import Schedule: Load a previously saved schedule file.\n\n" +
                "For more details, visit our website:\n";

        // Create a Hyperlink
        Hyperlink hyperlink = new Hyperlink("https://www.youtube.com"); // Replace with your URL
        hyperlink.setOnAction(event -> {
            MainScreen.getHostServicesInstance().showDocument(hyperlink.getText());
        });

        // Combine the text and hyperlink into a VBox
        VBox content = new VBox();
        Text text = new Text(aboutText);
        content.getChildren().addAll(text, hyperlink);
        content.setSpacing(10);

        // Set the VBox as the content of the dialog
        alert.getDialogPane().setContent(content);

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
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ViewCourses.fxml"));
       Parent root = fxmlLoader.load();



        //yeni stage oluştur ve .fxml'i göster
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Course Management");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

    @FXML
    private void startStudentPage () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Students.fxml"));
        Parent root = fxmlLoader.load();



        //yeni stage oluştur ve .fxml'i göster
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Course Management");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }
    @FXML
    private void startLecturerPage () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LecturerManagement.fxml"));
        Parent root = fxmlLoader.load();



        //yeni stage oluştur ve .fxml'i göster
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Course Management");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }
    @FXML
    private void startClassroomPage () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ClassroomsManagement.fxml"));
        Parent root = fxmlLoader.load();



        //yeni stage oluştur ve .fxml'i göster
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Course Management");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }
    @FXML
    private void startAttendancePage () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AttendanceManagement.fxml"));
        Parent root = fxmlLoader.load();



        //yeni stage oluştur ve .fxml'i göster
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Course Management");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }


    @FXML
    private void startStudentManagementPage () throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Students.fxml"));
        Parent root = fxmlLoader.load();

        //yeni stage oluştur ve .fxml'i göster
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Students List");
        stage.setScene(new Scene(root));
        stage.showAndWait();

    }

}

package com.example.demo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class ClassroomManagementController {

    @FXML
    private TableView<Classroom> classroomTable;
    @FXML
    private ObservableList<Course> courseData = FXCollections.observableArrayList();
    @FXML
    private TableColumn<Classroom, String> classroomColumn;
    @FXML
    private TableColumn<Course, String> courseColumn;
    @FXML
    private TableColumn<Classroom, Integer> capacityColumn;
    @FXML
    private TableColumn<Classroom, Void> actionColumn;

    private ObservableList<Classroom> masterData = FXCollections.observableArrayList();


    public void initialize() {
        // Tablo sütunlarını yapılandır
        classroomColumn.setCellValueFactory(new PropertyValueFactory<>("classroomName"));
        capacityColumn.setCellValueFactory(new PropertyValueFactory<>("capacity"));

        addButtonToTable();

        initializeMasterData();
    }
    public void initializeMasterData() {
        masterData = ClassroomDataAccessObject.getClassroomsForGUI();
        classroomTable.setItems(masterData);
    }
    private void addButtonToTable() {
        Callback<TableColumn<Classroom, Void>, TableCell<Classroom, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Classroom, Void> call(final TableColumn<Classroom, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Open Details");

                    {
                        btn.setOnAction(event -> {
                            Classroom data = getTableView().getItems().get(getIndex());
                            openDetailWindow(data);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        actionColumn.setCellFactory(cellFactory);
    }

    private void openDetailWindow(Classroom data)  {
        ClassroomController.currentClassroom = data;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Classroom.fxml"));
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
        stage.setTitle("Classroom");
        stage.setScene(new Scene(root));
        stage.showAndWait();
    }
    public void initializeCourseColumn () {
       // courseData = CourseDataAccessObject.ge
    }


}

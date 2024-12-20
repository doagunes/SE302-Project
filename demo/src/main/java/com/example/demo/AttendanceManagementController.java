package com.example.demo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class AttendanceManagementController {

        @FXML
        private javafx.scene.control.TextField searchBar;
        @FXML
        private Button searchBtn = new Button();
        @FXML
        private Button clearBBtn = new Button();
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
        @FXML
        private TableColumn<AttendanceStudents, Void> actionColumn;

        private ObservableList<AttendanceStudents> masterData = FXCollections.observableArrayList();


    public void initialize() {
            // Sütunları yapılandır
            idColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceId"));
            studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            absenceCountColumn.setCellValueFactory(new PropertyValueFactory<>("absenceCount"));

            addButtonToTable();

            //Verileri yükle
            masterData = AttendenceDatabase.getAttendanceStudents();
            tableView.setItems(masterData);
            // Verileri yükle
        }

        public void addButtonToTable () {
            Callback<TableColumn<AttendanceStudents, Void>, TableCell<AttendanceStudents, Void>> cellFactory = param -> new TableCell<>() {
                private final Button btn = new Button("ABSENT!");

                {
                    btn.setOnAction(event -> {
                        AttendanceStudents data = getTableView().getItems().get(getIndex());
                        AttendenceDatabase.incrementAbsenceCount(data);
                        initializeMasterData();
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

            actionColumn.setCellFactory(cellFactory);
        }
        @FXML
        public void searchBtnAction () {
            String searchText = searchBar.getText().toLowerCase();
            ObservableList<AttendanceStudents> filteredData = FXCollections.observableArrayList();

            for (AttendanceStudents attendanceStudents : masterData) {
                if (attendanceStudents.getStudentName().toLowerCase().contains(searchText) ||
                        attendanceStudents.getCourseName().toLowerCase().contains(searchText)) {
                    filteredData.add(attendanceStudents);
                }
            }

            tableView.setItems(filteredData);
        }

        @FXML
        public void clearBtnAction() {
            searchBar.clear();
            initializeMasterData();
            tableView.setItems(masterData); // Tüm verileri geri yükle
        }

        public void initializeMasterData() {
            masterData = AttendenceDatabase.getAttendanceStudents();
            tableView.setItems(masterData);
        }


}

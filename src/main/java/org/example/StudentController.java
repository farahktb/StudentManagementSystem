package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StudentController {

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TableView<Student> studentTable;

    @FXML
    private TableColumn<Student, Integer> idColumn;

    @FXML
    private TableColumn<Student, String> firstNameColumn;

    @FXML
    private TableColumn<Student, String> lastNameColumn;

    @FXML
    private TableColumn<Student, String> emailColumn;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    private final StudentDAO studentDAO = new StudentDAO();
    private final ObservableList<Student> studentList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getId()).asObject());
        firstNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFirstName()));
        lastNameColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getLastName()));
        emailColumn.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        loadStudents();

        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                txtFirstName.setText(newSelection.getFirstName());
                txtLastName.setText(newSelection.getLastName());
                txtEmail.setText(newSelection.getEmail());
            }
        });
    }

    private void loadStudents() {
        studentList.setAll(studentDAO.getAllStudents());
        studentTable.setItems(studentList);
    }

    @FXML
    private void handleAdd() {
        Student student = new Student(
                txtFirstName.getText(),
                txtLastName.getText(),
                txtEmail.getText()
        );
        studentDAO.addStudent(student);
        loadStudents();
        clearFields();
    }

    @FXML
    private void handleUpdate() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setFirstName(txtFirstName.getText());
            selected.setLastName(txtLastName.getText());
            selected.setEmail(txtEmail.getText());
            studentDAO.updateStudent(selected);
            loadStudents();
            clearFields();
        }
    }

    @FXML
    private void handleDelete() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            studentDAO.deleteStudent(selected.getId());
            loadStudents();
            clearFields();
        }
    }

    private void clearFields() {
        txtFirstName.clear();
        txtLastName.clear();
        txtEmail.clear();
        studentTable.getSelectionModel().clearSelection();
    }
}

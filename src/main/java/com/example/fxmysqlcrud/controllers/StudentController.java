package com.example.fxmysqlcrud.controllers;

import com.example.fxmysqlcrud.data.AppQuery;
import com.example.fxmysqlcrud.models.Student;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public TextField tfFirstname, tfMiddleName, tfLastname;
    public Button btnNew, btnSave, btnUpdate, btnDelete;
    public TableView<Student> tableView;

    public TableColumn<Student, Integer> colUid;
    public TableColumn<Student, String> colFirstname, colMiddleName, colLastname;

    private Student studentInfoCopy;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTableData();
        btnSave.setOnAction(this::onAddStudent);
        tableView.setOnMouseClicked(this::tblRowMouseClick);
        btnUpdate.setOnAction(this::onUpdateStudent);
        btnDelete.setOnAction(this::onDeleteStudent);
        btnNew.setOnAction(this::onNewStudent);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void initTableData(){
        AppQuery aq = new AppQuery();
        ObservableList<Student> list = aq.getStudentList();
        colUid.setCellValueFactory(new PropertyValueFactory<>("uid"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        colMiddleName.setCellValueFactory(new PropertyValueFactory<>("middlename"));
        colLastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        tableView.setItems(list);
    }

    public void onNewStudent(ActionEvent actionEvent){
        disableDelAndUpdateBtn();
        btnSave.setDisable(false);
        emptyFields();
    }

    public void onAddStudent(ActionEvent actionEvent){

        // get user inputs form text fields
        String firstname = tfFirstname.getText() != null ? tfFirstname.getText().trim() : "";
        String middleName = tfMiddleName.getText() != null ? tfMiddleName.getText().trim() : "";
        String lastname = tfLastname.getText() != null ? tfLastname.getText().trim() : "";

        // validations fields are not empty
        if(noInput("First Name", firstname)){return;}
        if(noInput("Middle Name", middleName)){return;}
        if(noInput("Last Name", lastname)){return;}


        Student student = new Student(firstname, middleName, lastname);
        AppQuery aq = new AppQuery();

        aq.addStudent(student); // add new student
        emptyFields(); // empty fields
        toastAlert("Success", "Saved Successfully!");

        // refresh table list
        initTableData();
    }

    private boolean noInput(String fieldName, String fieldContent){
        boolean yes = false;
        if(fieldContent.isEmpty()){
            yes = true;
            toastAlert("Oops!", fieldName+" Cannot be empty");
        }
        return  yes;
    }

    private void toastAlert(String headerText, String contentText){
        Alert alert;
        if(Objects.equals(headerText, "Oops!")){
            alert = new Alert(Alert.AlertType.ERROR);
        }else{
            alert = new Alert(Alert.AlertType.INFORMATION);
        }

        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }

    private void emptyFields(){
        tfFirstname.setText("");
        tfMiddleName.setText("");
        tfLastname.setText("");
    }

    private void tblRowMouseClick(MouseEvent mouseEvent) {
        Student student = tableView.getSelectionModel().getSelectedItem();
        this.studentInfoCopy = student;
        tfFirstname.setText(student.getFirstname());
        tfMiddleName.setText(student.getMiddlename());
        tfLastname.setText(student.getLastname());

        // enable update & delete buttons
        enableDelAndUpdateBtn();
        btnSave.setDisable(true);
    }

    private void onUpdateStudent(ActionEvent actionEvent){
        if(studentInfoCopy != null){
            String firstname = tfFirstname.getText() != null ? tfFirstname.getText().trim() : "";
            String middleName = tfMiddleName.getText() != null ? tfMiddleName.getText().trim() : "";
            String lastname = tfLastname.getText() != null ? tfLastname.getText().trim(): "";

            // ensure fields are not empty
            if(noInput("First Name", firstname)){ return;}
            if(noInput("Middle Name", middleName)){ return;}
            if(noInput("Last Name", lastname)){ return;}

            Student student = new Student(studentInfoCopy.getUid(), firstname, middleName, lastname);
            AppQuery aq = new AppQuery();
            final boolean updated = aq.updatedStudent(student);
            if(updated){
                toastAlert("Success", "Updated Successfully!");
            }else {
                toastAlert("Error", "Something Went Wrong During Update Process!");
            }

            emptyFields();
            this.studentInfoCopy = null;

            // refresh table list
            initTableData();
            disableDelAndUpdateBtn();
        }else{
            toastAlert("Error", "Record Id Missing!");
        }
    }

    private void onDeleteStudent(ActionEvent actionEvent){
        if(studentInfoCopy != null){
            AppQuery aq = new AppQuery();
            final boolean deleted = aq.deleteStudent(studentInfoCopy.getUid());
            if(deleted){
                toastAlert("Success", "Deleted Successfully!");
            }else {
                toastAlert("Error", "Something Went Wrong During Delete Process!");
            }

            emptyFields();
            this.studentInfoCopy = null;

            // refresh table list
            initTableData();

            // disable delete and update buttons
            disableDelAndUpdateBtn();
        }else{
            toastAlert("Error", "Record Id Missing!");
        }
    }

    public void disableNewAndSaveBtn(){
        btnNew.setDisable(true);
        btnSave.setDisable(true);
    }

    public void enableNewAndSaveBtn(){
        btnNew.setDisable(false);
        btnSave.setDisable(false);
    }

    private void disableDelAndUpdateBtn(){
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
    }

    private void enableDelAndUpdateBtn(){
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }
}

package com.example.fxmysqlcrud.controllers;

import com.example.fxmysqlcrud.data.AppQuery;
import com.example.fxmysqlcrud.models.Student;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    public TextField tfFirstname, tfMiddleName, tfLastname;
    public Button btnNew, btnSave, btnUpdate, btnDelete;
    public TableView<Student> tableView;

    public TableColumn<Student, Integer> colUid;
    public TableColumn<Student, String> colFirstname, colMiddleName, colLastname;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        btnSave.setOnAction(event -> onAddStudent());
        initTableData();
    }

    private void initTableData(){
        AppQuery aq = new AppQuery();
        ObservableList<Student> list = aq.getStudentList();
        colUid.setCellValueFactory(new PropertyValueFactory<Student, Integer>("uid"));
        colFirstname.setCellValueFactory(new PropertyValueFactory<Student, String>("firstname"));
        colMiddleName.setCellValueFactory(new PropertyValueFactory<Student, String>("middlename"));
        colLastname.setCellValueFactory(new PropertyValueFactory<Student, String>("lastname"));
        tableView.setItems(list);
    }

    private void onAddStudent(){
        // get user inputs form text fields
        String firstname = tfFirstname.getText() != null ? tfFirstname.getText().trim() : "";
        String middleName = tfMiddleName.getText() != null ? tfMiddleName.getText().trim() : "";
        String lastname = tfLastname.getText() != null ? tfLastname.getText().trim() : "";

        // validations fields are not empty
        if(noInputValidation("First Name", firstname)){return;};
        if(noInputValidation("Middle Name", middleName)){return;};
        if(noInputValidation("Last Name", lastname)){return;};


        Student student = new Student(firstname, middleName, lastname);
        AppQuery aq = new AppQuery();

        aq.addStudent(student); // add new student
        emptyFields(); // empty fields
        toastAlert("Success", "Saved Successfully!");
    }

    private boolean noInputValidation(String fieldName, String fieldContent){
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
}

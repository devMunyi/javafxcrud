module com.example.fxmysqlcrud {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens com.example.fxmysqlcrud to javafx.fxml;
    exports com.example.fxmysqlcrud;
    exports com.example.fxmysqlcrud.controllers;
    exports com.example.fxmysqlcrud.data;
    exports com.example.fxmysqlcrud.models;
}
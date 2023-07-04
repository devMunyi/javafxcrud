package com.example.fxmysqlcrud.data;

import com.example.fxmysqlcrud.models.Student;
import com.example.fxmysqlcrud.data.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class AppQuery {

    private final DBConnection dbConnection = new DBConnection();
    public void addStudent(Student student){

        try {
            dbConnection.createDBConn();
            PreparedStatement ps = dbConnection.getConn().prepareStatement("INSERT INTO students(firstname, middlename, lastname) VALUES(?, ?, ?)");
            ps.setString(1, student.getFirstname());
            ps.setString(2, student.getMiddlename());
            ps.setString(3, student.getLastname());
            ps.execute();
            ps.close();
            dbConnection.closeDBConn();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Student> getStudentList(){
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        ResultSet resultSet;
        Statement st;
        dbConnection.createDBConn();
        Connection conn = dbConnection.getConn();
        try {
            String query = "SELECT uid, firstname, middlename, lastname FROM students ORDER BY firstname ASC";
            st = conn.createStatement();
            resultSet = st.executeQuery(query);
            while (resultSet.next()){
                Integer uid = resultSet.getInt("uid");
                String firstname = resultSet.getString("firstname");
                String middleName = resultSet.getString("middlename");
                String lastname = resultSet.getString("lastname");

                studentList.add(new Student(uid, firstname, middleName, lastname));
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return studentList;
    }

}

package com.example.fxmysqlcrud.data;

import com.example.fxmysqlcrud.models.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class AppQuery {

    private final DBConnection dbConnDriver = new DBConnection();
    public void addStudent(Student student){

        try {
            dbConnDriver.createDBConn();
            PreparedStatement ps = dbConnDriver.getConn().prepareStatement("INSERT INTO students(firstname, middlename, lastname) VALUES(?, ?, ?)");
            ps.setString(1, student.getFirstname());
            ps.setString(2, student.getMiddlename());
            ps.setString(3, student.getLastname());
            ps.execute();
            ps.close();
            dbConnDriver.closeDBConn();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    public ObservableList<Student> getStudentList(){
        ObservableList<Student> studentList = FXCollections.observableArrayList();
        ResultSet resultSet;
        Statement st;
        dbConnDriver.createDBConn();
        Connection conn = dbConnDriver.getConn();
        try {
            String query = "SELECT uid, firstname, middlename, lastname FROM students ORDER BY uid ASC";
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

    public boolean updatedStudent(Student student){
        boolean updated = false;
        PreparedStatement ps;
        dbConnDriver.createDBConn();

        try {
            ps = dbConnDriver.getConn().prepareStatement("UPDATE `students`\n" +
                    "SET\n" +
                    "`firstname` = ?,\n" +
                    "`middlename` = ?,\n" +
                    "`lastname` = ?\n" +
                    "WHERE `uid` = ?;\n");

            ps.setString(1, student.getFirstname());
            ps.setString(2, student.getMiddlename());
            ps.setString(3, student.getLastname());
            ps.setInt(4, student.getUid());
            ps.execute();

            ps.close();
            dbConnDriver.closeDBConn();

            updated = true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        return updated;
    }

    public boolean deleteStudent(Integer uid){
        boolean deleted = false;
        PreparedStatement ps;
        dbConnDriver.createDBConn();

        try {
            ps = dbConnDriver.getConn().prepareStatement("DELETE FROM `students`\n" +
                    "WHERE uid = ?;\n");
            ps.setInt(1, uid);
            ps.execute();

            ps.close();
            dbConnDriver.closeDBConn();

            deleted = true;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return deleted;
    }

}

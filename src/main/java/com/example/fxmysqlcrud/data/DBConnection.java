package com.example.fxmysqlcrud.data;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static Connection conn;

    public void createDBConn(){
        synchronized (""){
            try {
                if(this.getConn() == null || this.getConn().isClosed()){
                    try {
                        String url = "jdbc:mysql://localhost/student_list";
                        // Class.forName(com.mysql.cj.jdbc.Driver);
                        setConn(DriverManager.getConnection(url, "root", ""));
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            }catch (Exception e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection aConn) {
        conn = aConn;
    }

    public void closeDBConn(){
        try {
            conn.close();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

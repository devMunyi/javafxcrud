package com.example.fxmysqlcrud.models;

public class Student {

    // set variables
    private Integer uid;
    private String firstname;
    private String middlename;
    private String lastname;


    // first constructor: to be used when inserting data into DB
    public Student(Integer uid, String firstname, String middlename, String lastname){
        this.uid = uid;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }

    // second constructor: to be used when collecting inputs
    public Student(String firstname, String middlename, String lastname){
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}

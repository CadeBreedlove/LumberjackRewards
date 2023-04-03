package com.example.lumberjackrewards;

public class UserModel {
    private String fName;
    private String lName;
    private String eMail;
    private String role;

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public UserModel(String fName, String lName, String eMail, String role) {
        this.fName = fName;
        this.lName = lName;
        this.eMail = eMail;
        this.role = role;
    }




}

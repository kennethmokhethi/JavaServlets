package model;

import java.util.ArrayList;

public class Student {

    private int s_No;
    private String s_Name;
    private String st_password;
    private String s_Surname;
    private String s_Degree;
    private ArrayList<Module> student_modules;

    public Student(String password, String s_Name, String s_Surname, String s_Degree) {
        setSt_password(password);
        setS_Name(s_Name);
        setS_Surname(s_Surname);
        setS_Degree(s_Degree);
        student_modules = new ArrayList<>();
    }

    public int getS_No() {
        return s_No;
    }

    public void setS_No(int s_No) {
        this.s_No = s_No;
    }

    public String getS_Name() {
        return s_Name;
    }

    public void setS_Name(String s_Name) {
        this.s_Name = s_Name;
    }

    public String getS_Surname() {
        return s_Surname;
    }

    public void setS_Surname(String s_Surname) {
        this.s_Surname = s_Surname;
    }

    public String getS_Degree() {
        return s_Degree;
    }

    public void setS_Degree(String s_Degree) {
        this.s_Degree = s_Degree;
    }

    public String getSt_password() {
        return st_password;
    }

    public void setSt_password(String st_password) {
        this.st_password = st_password;
    }

    public ArrayList<Module> getStudent_modules() {
        return student_modules;
    }

    public void addModule(Module module) {
        student_modules.add(module);
    }

}

package com.example.devul.schoolbackpack;

public class Grades {
    //private variables
    private int ID;
    private String subject;
    private String assignmentName;
    private int grade;
    private int percentWeigtage;

    //Empty Constructor
    public Grades(){

    }

    //Constructor
    public Grades(int ID, String subject, String assignmentName, int grade, int percentWeigtage){
        this.ID = ID;
        this.subject = subject;
        this.assignmentName = assignmentName;
        this.grade = grade;
        this.percentWeigtage = percentWeigtage;
    }

    //Constructor
    public Grades(String subject, String assignmentName, int grade, int percentWeigtage){
        this.subject = subject;
        this.assignmentName = assignmentName;
        this.grade = grade;
        this.percentWeigtage = percentWeigtage;
    }

    //Getter & Setter Methods
    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getPercentWeigtage() {
        return percentWeigtage;
    }

    public void setPercentWeigtage(int percentWeigtage) {
        this.percentWeigtage = percentWeigtage;
    }
}

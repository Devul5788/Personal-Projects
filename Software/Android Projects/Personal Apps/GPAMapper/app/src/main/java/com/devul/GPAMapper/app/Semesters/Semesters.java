package com.devul.GPAMapper.app.Semesters;

public class Semesters {
    private int ID, yearID, grade;
    private double percent;
    private String semesterName;

    public Semesters() {
    }

    public Semesters(int ID, int yearID, String semesterName, double percent, int grade) {
        this.ID = ID;
        this.yearID = yearID;
        this.semesterName = semesterName;
        this.percent = percent;
        this.grade = grade;
    }

    public Semesters(int yearID, String semesterName, double percent, int grade) {
        this.yearID = yearID;
        this.semesterName = semesterName;
        this.percent = percent;
        this.grade = grade;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getYearID() {
        return yearID;
    }

    public void setYearID(int yearID) {
        this.yearID = yearID;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}

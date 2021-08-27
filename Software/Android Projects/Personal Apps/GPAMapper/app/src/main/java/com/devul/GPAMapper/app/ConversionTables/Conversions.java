package com.devul.GPAMapper.app.ConversionTables;

public class Conversions {
    private int id, letterGrade;
    private double GPA;
    private String conversionTableName, percentage;

    public Conversions() {

    }

    public Conversions(int id, String conversionTableName, int letterGrade, String percentage, double GPA) {
        this.id = id;
        this.letterGrade = letterGrade;
        this.GPA = GPA;
        this.percentage = percentage;
        this.conversionTableName = conversionTableName;
    }

    public Conversions(String conversionTableName, int letterGrade, String percentage, double GPA) {
        this.letterGrade = letterGrade;
        this.GPA = GPA;
        this.conversionTableName = conversionTableName;
        this.percentage = percentage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConversionTableName() {
        return conversionTableName.trim();
    }

    public void setConversionTableName(String conversionTableName) {
        this.conversionTableName = conversionTableName.trim();
    }

    public int getLetterGrade() {
        return letterGrade;
    }

    public void setLetterGrade(int letterGrade) {
        this.letterGrade = letterGrade;
    }

    public double getGPA() {
        return GPA;
    }

    public void setGPA(double GPA) {
        this.GPA = GPA;
    }

    public String getPercentage() {
        return percentage.trim();
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage.trim();
    }
}

package com.devul.GPAMapper.app.Years;

public class Years {
    private int ID;
    private double percent;
    private String year;

    public Years() {
    }

    public Years(int ID, String year, double percent) {
        this.ID = ID;
        this.percent = percent;
        this.year = year;
    }

    public Years(String year, double percent) {
        this.percent = percent;
        this.year = year;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

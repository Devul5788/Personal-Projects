package com.devul.GPAMapper.app.Calculations;

public class ScoresForPercents {
    private int percent;
    private double sum;

    public ScoresForPercents() {

    }

    public ScoresForPercents(double sum, int percent) {
        this.sum = sum;
        this.percent = percent;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}

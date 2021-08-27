package com.devul.GPAMapper.app.Calculations;

public class ConversionNumbers {
    private Double lowerBound, upperBound;

    public ConversionNumbers() {
    }

    public ConversionNumbers(Double lowerBound, Double upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public Double getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(Double lowerBound) {
        this.lowerBound = lowerBound;
    }

    public Double getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(Double upperBound) {
        this.upperBound = upperBound;
    }
}

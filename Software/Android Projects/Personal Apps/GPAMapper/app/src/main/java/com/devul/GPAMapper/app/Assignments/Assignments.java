package com.devul.GPAMapper.app.Assignments;

import android.os.Parcel;
import android.os.Parcelable;

public class Assignments implements Parcelable {

    public static final Creator<Assignments> CREATOR = new Creator<Assignments>() {
        @Override
        public Assignments createFromParcel(Parcel in) {
            return new Assignments(in);
        }

        @Override
        public Assignments[] newArray(int size) {
            return new Assignments[size];
        }
    };

    public String assignmentName, date;
    public int ID, subjectID, percentWeightage, categoryImg, gradeImg, feelingNumber;
    double score;

    public Assignments() {
    }

    public Assignments(int ID, int subjectID, String assignmentName, double score,
                       int percentWeightage, String date, int categoryImg, int gradeImg, int feelingNumber) {
        this.assignmentName = assignmentName;
        this.date = date;
        this.ID = ID;
        this.subjectID = subjectID;
        this.score = score;
        this.percentWeightage = percentWeightage;
        this.categoryImg = categoryImg;
        this.gradeImg = gradeImg;
        this.feelingNumber = feelingNumber;
    }


    public Assignments(int subjectID, String assignmentName, double score,
                       int percentWeightage, String date, int categoryImg, int gradeImg, int feelingNumber) {
        this.assignmentName = assignmentName;
        this.date = date;
        this.subjectID = subjectID;
        this.score = score;
        this.percentWeightage = percentWeightage;
        this.categoryImg = categoryImg;
        this.gradeImg = gradeImg;
        this.feelingNumber = feelingNumber;
    }

    protected Assignments(Parcel in) {
        assignmentName = in.readString();
        date = in.readString();
        ID = in.readInt();
        subjectID = in.readInt();
        percentWeightage = in.readInt();
        categoryImg = in.readInt();
        gradeImg = in.readInt();
        feelingNumber = in.readInt();
        score = in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(assignmentName);
        parcel.writeString(date);
        parcel.writeInt(ID);
        parcel.writeInt(subjectID);
        parcel.writeInt(percentWeightage);
        parcel.writeInt(categoryImg);
        parcel.writeInt(gradeImg);
        parcel.writeInt(feelingNumber);
        parcel.writeDouble(score);
    }

    public String getAssignmentName() {
        return assignmentName.trim();
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getDate() {
        return date.trim();
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int getPercentWeightage() {
        return percentWeightage;
    }

    public void setPercentWeightage(int percentWeightage) {
        this.percentWeightage = percentWeightage;
    }

    public int getCategoryImg() {
        return categoryImg;
    }

    public void setCategoryImg(int categoryImg) {
        this.categoryImg = categoryImg;
    }

    public int getGradeImg() {
        return gradeImg;
    }

    public void setGradeImg(int gradeImg) {
        this.gradeImg = gradeImg;
    }

    public int getFeelingNumber() {
        return feelingNumber;
    }

    public void setFeelingNumber(int feelingNumber) {
        this.feelingNumber = feelingNumber;
    }
}

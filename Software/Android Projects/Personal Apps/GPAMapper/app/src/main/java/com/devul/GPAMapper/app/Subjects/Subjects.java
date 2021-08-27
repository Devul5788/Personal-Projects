package com.devul.GPAMapper.app.Subjects;

import android.os.Parcel;
import android.os.Parcelable;

public class Subjects implements Parcelable {

    public static final Creator<Subjects> CREATOR = new Creator<Subjects>() {
        @Override
        public Subjects createFromParcel(Parcel in) {
            return new Subjects(in);
        }

        @Override
        public Subjects[] newArray(int size) {
            return new Subjects[size];
        }
    };

    private String subject;
    private int ID, semesterID, grade;
    private int averageFeelingNumber;
    private double gradeP;

    public Subjects() {
    }

    public Subjects(int ID, int semesterID, String subject, double gradeP, int grade, int averageFeelingNumber) {
        this.subject = subject;
        this.ID = ID;
        this.semesterID = semesterID;
        this.grade = grade;
        this.averageFeelingNumber = averageFeelingNumber;
        this.gradeP = gradeP;
    }

    public Subjects(int semesterID, String subject, double gradeP, int grade) {
        this.subject = subject;
        this.semesterID = semesterID;
        this.grade = grade;
        this.gradeP = gradeP;
    }

    public Subjects(int semesterID, String subject, double gradeP, int grade, int averageFeelingNumber) {
        this.subject = subject;
        this.semesterID = semesterID;
        this.grade = grade;
        this.averageFeelingNumber = averageFeelingNumber;
        this.gradeP = gradeP;
    }

    protected Subjects(Parcel in) {
        subject = in.readString();
        ID = in.readInt();
        semesterID = in.readInt();
        grade = in.readInt();
        averageFeelingNumber = in.readInt();
        gradeP = in.readDouble();
    }

    public String getSubject() {
        return subject.trim();
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSemesterID() {
        return semesterID;
    }

    public void setSemesterID(int semesterID) {
        this.semesterID = semesterID;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getAverageFeelingNumber() {
        return averageFeelingNumber;
    }

    public void setAverageFeelingNumber(int averageFeelingNumber) {
        this.averageFeelingNumber = averageFeelingNumber;
    }

    public double getGradeP() {
        return gradeP;
    }

    public void setGradeP(double gradeP) {
        this.gradeP = gradeP;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subject);
        parcel.writeInt(ID);
        parcel.writeInt(semesterID);
        parcel.writeInt(grade);
        parcel.writeInt(averageFeelingNumber);
        parcel.writeDouble(gradeP);
    }
}
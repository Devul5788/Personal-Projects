package com.devul.GPAMapper.app.Categories;

public class Categories {

    String catName;
    int ID, subjectID, percentWeightage, categoryImg;

    public Categories() {
    }

    public Categories(int ID, int subjectID, String catName, int percentWeightage, int categoryImg) {
        this.catName = catName;
        this.ID = ID;
        this.subjectID = subjectID;
        this.percentWeightage = percentWeightage;
        this.categoryImg = categoryImg;
    }

    public Categories(int subjectID, String catName, int percentWeightage, int categoryImg) {
        this.catName = catName;
        this.subjectID = subjectID;
        this.percentWeightage = percentWeightage;
        this.categoryImg = categoryImg;
    }

    public Categories(String catName, int percentWeightage, int categoryImg) {
        this.catName = catName;
        this.percentWeightage = percentWeightage;
        this.categoryImg = categoryImg;
    }

    public String getCatName() {
        return catName.trim();
    }

    public void setCatName(String catName) {
        this.catName = catName;
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
}

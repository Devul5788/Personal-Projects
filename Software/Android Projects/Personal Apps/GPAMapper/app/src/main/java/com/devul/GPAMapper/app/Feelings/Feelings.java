package com.devul.GPAMapper.app.Feelings;

public class Feelings {
    private int ID, img, num;

    public Feelings() {
    }

    public Feelings(int ID, int img, int num) {
        this.ID = ID;
        this.img = img;
        this.num = num;
    }

    public Feelings(int img, int num) {
        this.img = img;
        this.num = num;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}

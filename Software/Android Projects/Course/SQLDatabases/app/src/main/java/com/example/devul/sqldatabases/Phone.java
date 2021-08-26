package com.example.devul.sqldatabases;

public class Phone {
    String name;
    String number;
    int id;

    //Empty Constructor
    public Phone(){

    }

    // constructor
    public Phone(String myName, String myNumber, int myID){
        name = myName;
        number = myNumber;
        id = myID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

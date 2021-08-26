package com.example.devul.storetextfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FileOS {
    static File f;
    public static boolean writeFile(File f, MyObject obj){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(obj);
            oos.close();
            return true;
        } catch (IOException ex) {
            System.out.println("Error");
            return false;
        }
    }

    public static MyObject readFile(File f){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            MyObject ipObject = (MyObject) ois.readObject();
            ois.close();
            return ipObject;
        } catch (IOException ex) {
            System.out.println("Writing Error");
        } catch (ClassNotFoundException ex) {
            System.err.println("Reading Error");
        }
        return null;
    }

    public static File getFile() {
        return f;
    }

    public static void setFile(File f) {
        FileOS.f = f;
    }

    public static boolean writeFile(MyObject ip){
        if(f == null)
            return false;
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
            oos.writeObject(ip);
            oos.close();
            return true;
        } catch (IOException ex) {
            System.out.println("Error");
            return false;
        }
    }

    public static MyObject readFile(){
        if(f == null)
            return null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            MyObject ipObject = (MyObject) ois.readObject();
            ois.close();
            return ipObject;
        } catch (IOException ex) {
            System.out.println("Writing Error");
        } catch (ClassNotFoundException ex) {
            System.err.println("Reading Error");
        }
        return null;
    }
}


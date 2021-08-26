package com.example.devul.schoolbackpack.LoginAndRegistration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class
FileOS {
    static File f;
    public static boolean writeFile(File f, com.example.devul.schoolbackpack.LoginAndRegistration.Object obj){
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

    public static com.example.devul.schoolbackpack.LoginAndRegistration.Object readFile(File f){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            com.example.devul.schoolbackpack.LoginAndRegistration.Object ipObject = (com.example.devul.schoolbackpack.LoginAndRegistration.Object) ois.readObject();
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
        com.example.devul.schoolbackpack.LoginAndRegistration.FileOS.f = f;
    }

    public static boolean writeFile(com.example.devul.schoolbackpack.LoginAndRegistration.Object ip){
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

    public static com.example.devul.schoolbackpack.LoginAndRegistration.Object readFile() {
        if (f == null)
            return null;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
            com.example.devul.schoolbackpack.LoginAndRegistration.Object ipObject = (com.example.devul.schoolbackpack.LoginAndRegistration.Object) ois.readObject();
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

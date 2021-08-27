package com.example.devul.schoolbackpack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database Version
    private static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "Student_GPA_Manager6";

    //Database table Name
    private static final String TABLE_GRADES = "grade";
    private static final String TABLE_SUBJECTS = "subjects";

    //Grades table column names
    private static final String KEY_ID = "id";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_ASSIGNMENT = "assignmentName";
    private static final String KEY_GRADE = "grade";
    private static final String KEY_WEIGHTAGE = "percentWeightage";

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        //Database Load or Creation
        String CREATE_GPACALCULATOR_TABLE = "CREATE TABLE " + TABLE_GRADES + "(" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SUBJECT + " INTEGER," + KEY_ASSIGNMENT + " INTEGER," +
                KEY_GRADE + " INTEGER," + KEY_WEIGHTAGE + ")";
        db.execSQL(CREATE_GPACALCULATOR_TABLE);

        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "(" + KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_SUBJECT + " TEXT" + ")";
        db.execSQL(CREATE_SUBJECTS_TABLE);
    }



    //For upgrading databases
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        //Drop older database if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GRADES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        //Create tables again
        onCreate(db);
    }

    public void addGrade(Grades grades) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        values.put(KEY_SUBJECT, grades.getSubject());
        values.put(KEY_ASSIGNMENT, grades.getAssignmentName());
        values.put(KEY_GRADE, grades.getGrade());
        values.put(KEY_WEIGHTAGE, grades.getPercentWeigtage());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_GRADES, null, values);

        //Closing Database connection
        db.close();
    }

    public List<Grades> getAllGrades(){
        List<Grades> gradesList = new ArrayList<Grades>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_GRADES.trim();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                Grades grades = new Grades();
                grades.setID(Integer.parseInt(cursor.getString(0)));
                grades.setSubject(cursor.getString(1));
                grades.setAssignmentName(cursor.getString(2));
                grades.setGrade(Integer.parseInt(cursor.getString(3)));
                grades.setPercentWeigtage(Integer.parseInt(cursor.getString(4)));
                gradesList.add(grades);
            } while (cursor.moveToNext());
        }
        return gradesList;
    }

    public void addSubjects(SubjectsG_S subjects) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        values.put(KEY_SUBJECT, subjects.getSubjects());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_SUBJECTS, null, values);

        //Closing Database connection
        db.close();
    }

    public List<SubjectsG_S> getAllSubjects(){
        List<SubjectsG_S> subjectsList = new ArrayList<SubjectsG_S>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS.trim();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if(cursor.moveToFirst()){
            do{
                SubjectsG_S subjects = new SubjectsG_S();
                subjects.setSubjects(cursor.getString(1));
                subjectsList.add(subjects);
            } while (cursor.moveToNext());
        }
        return subjectsList;
    }
}



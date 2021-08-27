package com.devul.GPAMapper.app.Other;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.devul.GPAMapper.app.Assignments.Assignments;
import com.devul.GPAMapper.app.Calculations.ScoresForPercents;
import com.devul.GPAMapper.app.Calculations.SubjectPercentCount;
import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.ConversionTables.Conversions;
import com.devul.GPAMapper.app.Feelings.Feelings;
import com.devul.GPAMapper.app.Semesters.Semesters;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.devul.GPAMapper.app.Years.Years;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    //Database Version
    public static final int DATABASE_VERSION = 1;

    //Database Name
    private static final String DATABASE_NAME = "Student_GPA_Manager";

    //Database table Name
    private static final String TABLE_YEAR = "years";
    private static final String TABLE_SEMESTER = "semesters";
    private static final String TABLE_SUBJECTS = "subjects";
    private static final String TABLE_ASSIGNMENTS = "assignments";
    private static final String TABLE_FEELINGS = "feelings";
    private static final String TABLE_CATEGORY = "categories";
    private static final String TABLE_CONVERSION = "conversionTable";

    //Database Column Names
    private static final String YEAR_KEY_ID = "YearID";
    private static final String SEMESTER_KEY_ID = "SemesterID";
    private static final String SUBJECTS_KEY_ID = "SubjectID";
    private static final String ASSIGNMENTS_KEY_ID = "AssignmentID";
    private static final String FEELINGS_KEY_ID = "feelingsID";
    private static final String CATEGORY_KEY_ID = "CategoryID";
    private static final String CONVERSION_KEY_ID = "conversionID";
    private static final String KEY_YEAR_NAME = "yearName";
    private static final String KEY_YEAR_GRADE_PERCENT = "yearGrade";
    private static final String KEY_SEMESTER_NAME = "semesterName";
    private static final String KEY_SEMESTER_PERCENT = "semesterPercent";
    private static final String KEY_SEMESTER_GRADE = "semesterGrade";
    private static final String KEY_SUBJECT_NAME = "subjectName";
    private static final String KEY_SUBJECT_GRADE_P = "subjectGradeP";
    private static final String KEY_SUBJECT_GRADE = "subjectGrade";
    private static final String KEY_AVERAGE_SUBJECT_FEELING_NUMBER = "averageSubjectFeeling";
    private static final String KEY_ASSIGNMENT_NAME = "assignmentName";
    private static final String KEY_SCORE_RECIEVED = "scoreRecieved";
    private static final String KEY_PERCENT_WEIGHTAGE = "percentWeightage";
    private static final String KEY_DATE = "date";
    private static final String KEY_CATEGORY_IMAGE_A = "categoryImgA";
    private static final String KEY_ASSIGNMENT_GRADE_IMAGE = "assignmentGradeImg";
    private static final String KEY_FEELINGS_IMAGE = "feelingsImage";
    private static final String KEY_FEELINGS_NUMBER = "feelingsNumber";
    private static final String KEY_CAT_NAME = "catergoryName";
    private static final String KEY_WEIGHTAGE = "percentWeightage";
    private static final String KEY_CATEGORY_IMAGE_C = "categoryImgC";
    private static final String KEY_GPA_CONVERSION_TABLE = "GPAConversionTable";
    private static final String KEY_LETTER_GRADE = "letterGrade";
    private static final String KEY_PERCENTAGE = "percentage";
    private static final String KEY_GPA = "GPA";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Database Load or Creation
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_YEAR_TABLE = "CREATE TABLE " + TABLE_YEAR + "(" + YEAR_KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_YEAR_NAME + " TEXT, " + KEY_YEAR_GRADE_PERCENT + " DOUBLE" + ")";
        db.execSQL(CREATE_YEAR_TABLE);

        String CREATE_SEMESTER_TABLE = "CREATE TABLE " + TABLE_SEMESTER + "(" + SEMESTER_KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + YEAR_KEY_ID + " INTEGER, " + KEY_SEMESTER_NAME +
                " TEXT, " + KEY_SEMESTER_PERCENT + " DOUBLE, " + KEY_SEMESTER_GRADE + " INTEGER" + ")";
        db.execSQL(CREATE_SEMESTER_TABLE);

        String CREATE_SUBJECTS_TABLE = "CREATE TABLE " + TABLE_SUBJECTS + "(" + SUBJECTS_KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + SEMESTER_KEY_ID + " INTEGER, " + KEY_SUBJECT_NAME
                + " TEXT," + KEY_SUBJECT_GRADE_P + " DOUBLE, " + KEY_SUBJECT_GRADE + " INTEGER, " +
                KEY_AVERAGE_SUBJECT_FEELING_NUMBER + " INTEGER " + ")";
        db.execSQL(CREATE_SUBJECTS_TABLE);

        String CREATE_ASSIGNMENTS_TABLE = "CREATE TABLE " + TABLE_ASSIGNMENTS + "(" + ASSIGNMENTS_KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + SUBJECTS_KEY_ID + " INTEGER, " + KEY_ASSIGNMENT_NAME + " TEXT, " +
                KEY_SCORE_RECIEVED + " DOUBLE, " + KEY_PERCENT_WEIGHTAGE + " INTEGER, " + KEY_DATE + " DATE, "
                + KEY_CATEGORY_IMAGE_A + " INTEGER, " + KEY_ASSIGNMENT_GRADE_IMAGE + " INTEGER, " +
                KEY_FEELINGS_NUMBER + " INTEGER " + ")";
        db.execSQL(CREATE_ASSIGNMENTS_TABLE);

        String CREATE_FEELINGS_TABLE = "CREATE TABLE " + TABLE_FEELINGS + "(" + FEELINGS_KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FEELINGS_IMAGE + " INTEGER, " +
                KEY_FEELINGS_NUMBER + " INTEGER" + ")";
        db.execSQL(CREATE_FEELINGS_TABLE);

        String CREATE_CATEGORY_TABLE = "CREATE TABLE " + TABLE_CATEGORY + "(" + CATEGORY_KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + SUBJECTS_KEY_ID + " INTEGER, " + KEY_CAT_NAME +
                " TEXT, " + KEY_WEIGHTAGE + " INTEGER, " + KEY_CATEGORY_IMAGE_C + " INTEGER " + ")";
        db.execSQL(CREATE_CATEGORY_TABLE);

        String CREATE_CONVERSION_TABLE = "CREATE TABLE " + TABLE_CONVERSION + "(" + CONVERSION_KEY_ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_GPA_CONVERSION_TABLE + " TEXT, " + KEY_LETTER_GRADE +
                " INTEGER, " + KEY_PERCENTAGE + " TEXT, " + KEY_GPA + " DOUBLE " + ")";
        db.execSQL(CREATE_CONVERSION_TABLE);
    }

    //For upgrading databases
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop older database if it exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YEAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SEMESTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SUBJECTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSIGNMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FEELINGS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONVERSION);
        //Create tables again
        onCreate(db);
    }

    //YEAR CRUD METHODS

    // Adding a single year
    public void addYear(Years years) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        values.put(KEY_YEAR_NAME, years.getYear());
        values.put(KEY_YEAR_GRADE_PERCENT, years.getPercent());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_YEAR, null, values);

        //Closing Database connection
        db.close();
    }

    // Getting single year
    public Years getYear(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_YEAR, new String[]{YEAR_KEY_ID,
                        KEY_YEAR_NAME, KEY_YEAR_GRADE_PERCENT}, YEAR_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Years(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2));
    }

    //Get all the years
    public ArrayList<Years> getAllYears(boolean filter, String FilterName, String FilterSeq) {
        ArrayList<Years> yearsList = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_YEAR.trim();

        if (filter) {
            if (FilterName.equals("Name")) {
                if (FilterSeq.equals("Ascending")) {
                    selectQuery = "SELECT  * FROM " + TABLE_YEAR.trim() + " order by " + KEY_YEAR_NAME;
                } else if (FilterSeq.equals("Descending")) {
                    selectQuery = "SELECT  * FROM " + TABLE_YEAR.trim() + " order by " + KEY_YEAR_NAME + " desc";
                }
            } else if (FilterName.equals("Scores")) {
                if (FilterSeq.equals("Ascending")) {
                    selectQuery = "SELECT  * FROM " + TABLE_YEAR.trim() + " order by " + KEY_YEAR_GRADE_PERCENT + " desc";
                } else if (FilterSeq.equals("Descending")) {
                    selectQuery = "SELECT  * FROM " + TABLE_YEAR.trim() + " order by " + KEY_YEAR_GRADE_PERCENT;
                }
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Years year = new Years();
                year.setID(cursor.getInt(0));
                year.setYear(cursor.getString(1));
                year.setPercent(cursor.getDouble(2));
                yearsList.add(year);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return yearsList;
    }

    // Getting years count
    public int getYearsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_YEAR;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = cursor.getCount();
        cursor.close();

        // return count
        return num;
    }

    // Updating single year
    public void updateYear(Years y) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_YEAR_NAME, y.getYear());
        values.put(KEY_YEAR_GRADE_PERCENT, y.getPercent());

        // updating row
        db.update(TABLE_YEAR, values, YEAR_KEY_ID + " = ?",
                new String[]{String.valueOf(y.getID())});
    }

    // Deleting single year
    public void deleteYear(Years y) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_YEAR, YEAR_KEY_ID + " = ?",
                new String[]{String.valueOf(y.getID())
                });
        db.close();
    }

    //Delete all years
    public void deleteAllYears() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from years");
        db.close();
    }

    //Semester CRUD METHODS

    // Adding a single semester
    public void addSemester(Semesters semesters) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        values.put(YEAR_KEY_ID, semesters.getYearID());
        values.put(KEY_SEMESTER_NAME, semesters.getSemesterName());
        values.put(KEY_SEMESTER_PERCENT, semesters.getPercent());
        values.put(KEY_SEMESTER_GRADE, semesters.getGrade());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_SEMESTER, null, values);

        //Closing Database connection
        db.close();
    }

    // Getting single semester
    public Semesters getSemesters(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SEMESTER, new String[]{SEMESTER_KEY_ID, YEAR_KEY_ID,
                        KEY_SEMESTER_NAME, KEY_SEMESTER_PERCENT, KEY_SEMESTER_GRADE}, SEMESTER_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Semesters(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                cursor.getInt(3), cursor.getInt(4));
    }

    //Get all the semesters
    public ArrayList<Semesters> getAllSemesters(boolean filter, String FilterName, String FilterSeq) {
        ArrayList<Semesters> semestersList = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_SEMESTER.trim();

        if (filter) {
            if (FilterName.equals("Name")) {
                if (FilterSeq.equals("Ascending")) {
                    selectQuery = "SELECT  * FROM " + TABLE_SEMESTER.trim() + " order by " + KEY_SEMESTER_NAME;
                } else if (FilterSeq.equals("Descending")) {
                    selectQuery = "SELECT  * FROM " + TABLE_SEMESTER.trim() + " order by " + KEY_SEMESTER_NAME + " desc";
                }
            } else if (FilterName.equals("Scores")) {
                if (FilterSeq.equals("Ascending")) {
                    selectQuery = "SELECT  * FROM " + TABLE_SEMESTER.trim() + " order by " + KEY_SEMESTER_PERCENT + " desc";
                } else if (FilterSeq.equals("Descending")) {
                    selectQuery = "SELECT  * FROM " + TABLE_SEMESTER.trim() + " order by " + KEY_SEMESTER_PERCENT;
                }
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Semesters semesters = new Semesters();
                semesters.setID(cursor.getInt(0));
                semesters.setYearID(cursor.getInt(1));
                semesters.setSemesterName(cursor.getString(2));
                semesters.setPercent(cursor.getInt(3));
                semesters.setGrade(cursor.getInt(4));
                semestersList.add(semesters);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return semestersList;
    }

    // Getting semesters count
    public int getSemestersCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SEMESTER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = cursor.getCount();
        cursor.close();

        // return count
        return num;
    }

    //update a single semester
    public void updateSemester(Semesters s) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SEMESTER_KEY_ID, s.getID());
        values.put(YEAR_KEY_ID, s.getYearID());
        values.put(KEY_SEMESTER_NAME, s.getSemesterName());
        values.put(KEY_SEMESTER_PERCENT, s.getPercent());
        values.put(KEY_SEMESTER_GRADE, s.getGrade());

        // updating row
        db.update(TABLE_SEMESTER, values, SEMESTER_KEY_ID + " = ?",
                new String[]{String.valueOf(s.getID())});
    }

    // Deleting single semester
    public void deleteSemester(Semesters s) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SEMESTER, SEMESTER_KEY_ID + " = ?",
                new String[]{String.valueOf(s.getID())
                });
        db.close();
    }

    //Delete all semesters
    public void deleteAllSemesters() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from semesters");
        db.close();
    }

    //SUBJECT CRUD METHODS

    //Add a subject
    public void addSubject(Subjects subjects) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        values.put(SEMESTER_KEY_ID, subjects.getSemesterID());
        values.put(KEY_SUBJECT_NAME, subjects.getSubject());
        values.put(KEY_SUBJECT_GRADE_P, subjects.getGradeP());
        values.put(KEY_SUBJECT_GRADE, subjects.getGrade());
        values.put(KEY_AVERAGE_SUBJECT_FEELING_NUMBER, subjects.getAverageFeelingNumber());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_SUBJECTS, null, values);

        //Closing Database connection
        db.close();
    }

    // Getting single subject
    public Subjects getSubject(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_SUBJECTS, new String[]{SUBJECTS_KEY_ID, YEAR_KEY_ID, SEMESTER_KEY_ID,
                        KEY_SUBJECT_NAME, KEY_SUBJECT_GRADE_P, KEY_SUBJECT_GRADE, KEY_AVERAGE_SUBJECT_FEELING_NUMBER}, SUBJECTS_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        // return contact

        return new Subjects(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getDouble(3),
                cursor.getInt(4), cursor.getInt(5));
    }

    //Get all the subjects
    public ArrayList<Subjects> getAllSubjects(boolean filter, String FilterName, String FilterSeq) {
        ArrayList<Subjects> subjectsList = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS.trim();

        if (filter) {
            switch (FilterName) {
                case "Name":
                    if (FilterSeq.equals("Ascending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS.trim() + " order by " + KEY_SUBJECT_NAME;
                    } else if (FilterSeq.equals("Descending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS.trim() + " order by " + KEY_SUBJECT_NAME + " desc";
                    }
                    break;
                case "Grades":
                    if (FilterSeq.equals("Ascending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS.trim() + " order by " + KEY_SUBJECT_GRADE_P;
                    } else if (FilterSeq.equals("Descending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS.trim() + " order by " + KEY_SUBJECT_GRADE_P + " desc";
                    }
                    break;
                case "Feelings":
                    if (FilterSeq.equals("Ascending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS.trim() + " order by " + KEY_AVERAGE_SUBJECT_FEELING_NUMBER + " desc";
                    } else if (FilterSeq.equals("Descending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_SUBJECTS.trim() + " order by " + KEY_AVERAGE_SUBJECT_FEELING_NUMBER;
                    }
                    break;
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Subjects subjects = new Subjects();
                subjects.setID(cursor.getInt(0));
                subjects.setSemesterID(cursor.getInt(1));
                subjects.setSubject(cursor.getString(2));
                subjects.setGradeP(cursor.getDouble(3));
                subjects.setGrade(cursor.getInt(4));
                subjects.setAverageFeelingNumber(cursor.getInt(5));
                subjectsList.add(subjects);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return subjectsList;
    }

    // Getting subjects count
    public int getSubjectsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_SUBJECTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = cursor.getCount();
        cursor.close();

        // return count
        return num;
    }

    // Updating single subject
    public void updateSubject(Subjects s) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUBJECTS_KEY_ID, s.getID());
        values.put(SEMESTER_KEY_ID, s.getSemesterID());
        values.put(KEY_SUBJECT_NAME, s.getSubject());
        values.put(KEY_SUBJECT_GRADE_P, s.getGradeP());
        values.put(KEY_SUBJECT_GRADE, s.getGrade());
        values.put(KEY_AVERAGE_SUBJECT_FEELING_NUMBER, s.getAverageFeelingNumber());

        // updating row
        db.update(TABLE_SUBJECTS, values, SUBJECTS_KEY_ID + " = ?",
                new String[]{String.valueOf(s.getID())});
    }

    // Deleting single subject
    public void deleteSubject(Subjects sb) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SUBJECTS, SUBJECTS_KEY_ID + " = ?",
                new String[]{String.valueOf(sb.getID())
                });
        db.close();
    }

    //Delete all subjects
    public void deleteAllSubjects() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from subjects");
        db.close();
    }

    //ASSIGNMENT CRUD METHODS

    //Adding an assignment
    public void addAssignment(Assignments a) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        values.put(SUBJECTS_KEY_ID, a.getSubjectID());
        values.put(KEY_ASSIGNMENT_NAME, a.getAssignmentName());
        values.put(KEY_SCORE_RECIEVED, a.getScore());
        values.put(KEY_PERCENT_WEIGHTAGE, a.getPercentWeightage());
        values.put(KEY_DATE, a.getDate());
        values.put(KEY_CATEGORY_IMAGE_A, a.getCategoryImg());
        values.put(KEY_ASSIGNMENT_GRADE_IMAGE, a.getGradeImg());
        values.put(KEY_FEELINGS_NUMBER, a.getFeelingNumber());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_ASSIGNMENTS, null, values);

        //Closing Database connection
        db.close();
    }

    //Get a single assignment
    public Assignments getAssignment(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_ASSIGNMENTS, new String[]{ASSIGNMENTS_KEY_ID,
                        SUBJECTS_KEY_ID, KEY_ASSIGNMENT_NAME, KEY_SCORE_RECIEVED, KEY_PERCENT_WEIGHTAGE, KEY_DATE,
                        KEY_CATEGORY_IMAGE_A, KEY_ASSIGNMENT_GRADE_IMAGE, KEY_FEELINGS_NUMBER},
                ASSIGNMENTS_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Assignments(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                cursor.getDouble(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6),
                cursor.getInt(7), cursor.getInt(8));
    }

    //Getting all assignments
    public List<Assignments> getAllAssignments(Boolean filter, String FilterName, String FilterSeq) {
        List<Assignments> AssignmentsList = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim();

        if (filter) {
            switch (FilterName) {
                case "Name":
                    if (FilterSeq.equals("Ascending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim() + " order by " + KEY_ASSIGNMENT_NAME;
                    } else if (FilterSeq.equals("Descending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim() + " order by " + KEY_ASSIGNMENT_NAME + " desc";
                    }
                    break;
                case "Grades":
                    if (FilterSeq.equals("Ascending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim() + " order by " + KEY_SCORE_RECIEVED + " desc";
                    } else if (FilterSeq.equals("Descending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim() + " order by " + KEY_SCORE_RECIEVED;
                    }
                    break;
                case "Feelings":
                    if (FilterSeq.equals("Ascending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim() + " order by " + KEY_FEELINGS_NUMBER + " desc";
                    } else if (FilterSeq.equals("Descending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim() + " order by " + KEY_FEELINGS_NUMBER;
                    }
                    break;
                case "Date":
                    if (FilterSeq.equals("Ascending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim() + " order by " + KEY_DATE;
                    } else if (FilterSeq.equals("Descending")) {
                        selectQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS.trim() + " order by " + KEY_DATE + " desc";
                    }
                    break;
            }
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Assignments assignment = new Assignments();
                assignment.setID(cursor.getInt(0));
                assignment.setSubjectID(cursor.getInt(1));
                assignment.setAssignmentName(cursor.getString(2));
                assignment.setScore(cursor.getDouble(3));
                assignment.setPercentWeightage(cursor.getInt(4));
                assignment.setDate(cursor.getString(5));
                assignment.setCategoryImg(cursor.getInt(6));
                assignment.setGradeImg(cursor.getInt(7));
                assignment.setFeelingNumber(cursor.getInt(8));
                AssignmentsList.add(assignment);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return AssignmentsList;
    }

    //Get Assignments count
    public int getAssignmentsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_ASSIGNMENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = cursor.getCount();
        cursor.close();

        // return count
        return num;
    }

    // Updating single contact
    public void updateAssignment(Assignments a) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUBJECTS_KEY_ID, a.getSubjectID());
        values.put(KEY_ASSIGNMENT_NAME, a.getAssignmentName());
        values.put(KEY_SCORE_RECIEVED, a.getScore());
        values.put(KEY_PERCENT_WEIGHTAGE, a.getPercentWeightage());
        values.put(KEY_DATE, a.getDate());
        values.put(KEY_CATEGORY_IMAGE_A, a.getCategoryImg());
        values.put(KEY_ASSIGNMENT_GRADE_IMAGE, a.getGradeImg());
        values.put(KEY_FEELINGS_NUMBER, a.getFeelingNumber());

        // updating row
        db.update(TABLE_ASSIGNMENTS, values, ASSIGNMENTS_KEY_ID + " = ?",
                new String[]{String.valueOf(a.getID())});
    }

    // Deleting single contact
    public void deleteAssignment(Assignments assignment) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSIGNMENTS, ASSIGNMENTS_KEY_ID + " = ?",
                new String[]{String.valueOf(assignment.getID())
                });
        db.close();
    }

    //Delete all assignments
    public void deleteAllAssignment() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from assignments");
        db.close();
    }

    //FEELINGS CRUD METHODS

    // Adding a single feeling
    public void addFeelings(Feelings feelings) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        values.put(KEY_FEELINGS_IMAGE, feelings.getImg());
        values.put(KEY_FEELINGS_NUMBER, feelings.getNum());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_FEELINGS, null, values);

        //Closing Database connection
        db.close();
    }

    // Getting single feeling
    public Feelings getFeelings(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_YEAR, new String[]{FEELINGS_KEY_ID,
                        KEY_FEELINGS_IMAGE, KEY_FEELINGS_NUMBER}, FEELINGS_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        // return contact

        return new Feelings(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
    }

    //Get all the feelings
    public ArrayList<Feelings> getAllFeelings() {
        ArrayList<Feelings> feelingsList = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_FEELINGS.trim();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Feelings feelings = new Feelings();
                feelings.setID(cursor.getInt(0));
                feelings.setImg(cursor.getInt(1));
                feelings.setNum(cursor.getInt(2));
                feelingsList.add(feelings);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return feelingsList;
    }

    // Getting feelings count
    public int getFeelingsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FEELINGS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = cursor.getCount();
        cursor.close();

        // return count
        return num;
    }

    // Updating single feeling
    public int updateFeelings(Feelings f) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FEELINGS_KEY_ID, f.getID());
        values.put(KEY_FEELINGS_IMAGE, f.getImg());
        values.put(KEY_FEELINGS_NUMBER, f.getNum());

        // updating row
        return db.update(TABLE_FEELINGS, values, FEELINGS_KEY_ID + " = ?",
                new String[]{String.valueOf(f.getID())});
    }

    // Deleting single feeling
    public void deleteFeelings(Feelings f) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FEELINGS, FEELINGS_KEY_ID + " = ?",
                new String[]{String.valueOf(f.getID())
                });
        db.close();
    }

    //Delete all feelings
    public void deleteAllFeelings() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from feelings");
        db.close();
    }


    //CATEGORY CRUD METHODS

    //Adding an category
    public void addCategory(Categories category) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        //values.put(CATEGORY_KEY_ID, category.getID());

        values.put(SUBJECTS_KEY_ID, category.getSubjectID());
        values.put(KEY_CAT_NAME, category.getCatName());
        values.put(KEY_WEIGHTAGE, category.getPercentWeightage());
        values.put(KEY_CATEGORY_IMAGE_C, category.getCategoryImg());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_CATEGORY, null, values);

        //Closing Database connection
        db.close();
    }

    //Get a category
    public Categories getCategory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEGORY, new String[]{CATEGORY_KEY_ID, SUBJECTS_KEY_ID,
                        KEY_CAT_NAME, KEY_WEIGHTAGE, KEY_CATEGORY_IMAGE_C}, CATEGORY_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        return new Categories(cursor.getInt(0), cursor.getInt(1), cursor.getString(2),
                cursor.getInt(3), cursor.getInt(4));
    }

    //Get all categories
    public List<Categories> getAllCategories() {
        List<Categories> categoriesList = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORY.trim();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list

        if (cursor.moveToFirst()) {
            do {
                Categories category = new Categories();
                category.setID(cursor.getInt(0));
                category.setSubjectID(cursor.getInt(1));
                category.setCatName(cursor.getString(2));
                category.setPercentWeightage(cursor.getInt(3));
                category.setCategoryImg(cursor.getInt(4));

                categoriesList.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categoriesList;
    }

    public int getCategoriesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CATEGORY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = cursor.getCount();
        cursor.close();

        // return count
        return num;
    }

    // Updating single contact
    public int updateCategory(Categories c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SUBJECTS_KEY_ID, c.getSubjectID());
        values.put(KEY_CAT_NAME, c.getCatName());
        values.put(KEY_WEIGHTAGE, c.getPercentWeightage());
        values.put(KEY_CATEGORY_IMAGE_C, c.getCategoryImg());

        // updating row
        return db.update(TABLE_CATEGORY, values, CATEGORY_KEY_ID + " = ?",
                new String[]{String.valueOf(c.getID())});
    }

    //Delete a Category
    public void deleteCategory(Categories c) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEGORY, CATEGORY_KEY_ID + " = ?",
                new String[]{String.valueOf(c.getID())
                });
        db.close();
    }

    //Delete all Categories
    public void deleteAllCategories() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from categories");
        db.close();
    }

    //CONVERSION CRUD METHODS

    //Add a conversion
    public void addConversion(Conversions ct) {
        //Creating a new database
        SQLiteDatabase db = this.getWritableDatabase();

        //Creating a new ContentValue
        ContentValues values = new ContentValues();

        //Inserting values into the ContentValue
        values.put(KEY_GPA_CONVERSION_TABLE, ct.getConversionTableName());
        values.put(KEY_LETTER_GRADE, ct.getLetterGrade());
        values.put(KEY_PERCENTAGE, ct.getPercentage());
        values.put(KEY_GPA, ct.getGPA());

        //Inserting values from ContentView to Database's rows
        db.insert(TABLE_CONVERSION, null, values);

        //Closing Database connection
        db.close();
    }

    //Get all Conversions
    public List<Conversions> getAllConversions() {
        List<Conversions> conversionList = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT  * FROM " + TABLE_CONVERSION.trim();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Conversions conversion = new Conversions();
                conversion.setId(cursor.getInt(0));
                conversion.setConversionTableName(cursor.getString(1));
                conversion.setLetterGrade(cursor.getInt(2));
                conversion.setPercentage(cursor.getString(3));
                conversion.setGPA(cursor.getDouble(4));
                conversionList.add(conversion);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return conversionList;
    }

    //Get conversion count
    public int getConversionsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONVERSION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int num = cursor.getCount();
        cursor.close();

        // return count
        return num;
    }

    // Updating single conversion
    public void updateConversion(Conversions c) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_GPA_CONVERSION_TABLE, c.getConversionTableName());
        values.put(KEY_LETTER_GRADE, c.getLetterGrade());
        values.put(KEY_PERCENTAGE, c.getPercentage());
        values.put(KEY_GPA, c.getGPA());

        // updating row
        db.update(TABLE_CONVERSION, values, CONVERSION_KEY_ID + " = ?", new String[]{String.valueOf(c.getId())});
    }

    //Delete a Conversion
    public void deleteConversion(Conversions c) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONVERSION, CONVERSION_KEY_ID + " = ?",
                new String[]{String.valueOf(c.getId())
                });
        db.close();
    }

    //Delete all conversion
    public void deleteAllConversions() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from conversionTable");
        db.close();
    }

    public List<SubjectPercentCount> getAllAssignmentsByPercent(int SubjectId) {
        List<SubjectPercentCount> percentCounts = new ArrayList<>();

        //Select all query
        String selectQuery = "SELECT percentWeightage,count(percentWeightage) as cnt  FROM  assignments " +
                "where SubjectID =" + SubjectId + " group by percentWeightage";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SubjectPercentCount pc = new SubjectPercentCount();
                pc.setPercent(cursor.getInt(0));
                pc.setCount(cursor.getInt(1));
                percentCounts.add(pc);
            } while (cursor.moveToNext());
        }
        return percentCounts;
    }

    public List<ScoresForPercents> getScoreForPercent(int SubjectId) {
        List<ScoresForPercents> PercentScores = new ArrayList<>();

        String selectQuery = "SELECT percentWeightage, sum(scoreRecieved) as sm  FROM  assignments " +
                "where SubjectID =" + SubjectId + " group by percentWeightage";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        //looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ScoresForPercents ps = new ScoresForPercents();
                ps.setPercent(cursor.getInt(0));
                ps.setSum(cursor.getDouble(1));
                PercentScores.add(ps);
            } while (cursor.moveToNext());
        }
        return PercentScores;
    }
}
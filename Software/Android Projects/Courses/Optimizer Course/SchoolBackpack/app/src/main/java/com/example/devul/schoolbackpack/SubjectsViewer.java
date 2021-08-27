package com.example.devul.schoolbackpack;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Subjects extends AppCompatActivity {
    EditText subjectName;
    Button addSubject;
    ArrayList<String> al = new ArrayList<String>();
    Spinner spnsubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subjects);
        subjectName = (EditText)findViewById(R.id.subjectName);
        addSubject = (Button)findViewById(R.id.addSubject);
        spnsubjects = (Spinner)findViewById(R.id.showSubjects);
        DatabaseHandler db = new DatabaseHandler(Subjects.this);
        db.addSubjects(new SubjectsG_S(subjectName.getText().toString()));
        List<SubjectsG_S> subject = db.getAllSubjects();
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, al);
        spnsubjects.setAdapter(listAdapter);
        spnsubjects.setAdapter(listAdapter);
        addSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(Subjects.this);
                db.addSubjects(new SubjectsG_S(subjectName.getText().toString()));
                List<SubjectsG_S> subject = db.getAllSubjects();
                for (SubjectsG_S s: subject) {
                    String log = "Subject: " + s.getSubjects();
                    Toast.makeText(Subjects.this,log , Toast.LENGTH_LONG).show();
                }
                }
            });
    }
    DatabaseHandler db = new DatabaseHandler(Subjects.this);
        db.addSubjects(new SubjectsG_S(subjectName.getText().toString()));
    List<SubjectsG_S> subject = db.getAllSubjects();
    ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, al);
        spnsubjects.setAdapter(listAdapter);
        spnsubjects.setAdapter(listAdapter);
}

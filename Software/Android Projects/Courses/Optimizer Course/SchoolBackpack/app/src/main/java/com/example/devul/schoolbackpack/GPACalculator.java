package com.example.devul.schoolbackpack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GPACalculator extends AppCompatActivity {

    EditText subject, assignmentName, grade, percentWeightage;
    Button setValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpacalculator);
        subject = (EditText) findViewById(R.id.subject);
        assignmentName = (EditText) findViewById(R.id.assignmentName);
        grade = (EditText) findViewById(R.id.grade);
        percentWeightage = (EditText) findViewById(R.id.percentWeightage);
        setValues = (Button) findViewById(R.id.setValues);

        setValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(GPACalculator.this);
                db.addGrade(new Grades(subject.getText().toString(), assignmentName.getText().toString(),
                        Integer.parseInt(grade.getText().toString()), Integer.parseInt(percentWeightage.getText().toString())));
                startActivity(new Intent(GPACalculator.this, GPACalcViewer.class));
            }
        });
    }
}

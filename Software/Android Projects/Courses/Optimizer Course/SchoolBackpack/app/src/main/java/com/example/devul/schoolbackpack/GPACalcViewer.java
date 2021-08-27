package com.example.devul.schoolbackpack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GPACalcViewer extends AppCompatActivity {

    ArrayList<String> al = new ArrayList<String>();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpacalc_viewer);
        listView = (ListView)findViewById(R.id.viewer);

        DatabaseHandler db = new DatabaseHandler(GPACalcViewer.this);
        List<Grades> grades = db.getAllGrades();
        for (Grades gp : grades) {
            if(gp.getGrade() >=0 && gp.getGrade() <= 100 && gp.getPercentWeigtage() >= 0
                    && gp.getPercentWeigtage() <=100){
                String log = "Id: " + gp.getID()+ " ,Subject: " + gp.getSubject() + " ,Assignment Name: "
                        + gp.getAssignmentName() + " ,Grade: " + gp.getGrade() + " ,Percent Weightage:"+
                        gp.getPercentWeigtage();
                al.add(log);
            }
            else{
                Toast.makeText(this, "Please make sure that your Grade and Percent" +
                        " Weigtage is between 0 and 100.", Toast.LENGTH_LONG).show();
            }
        }
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(GPACalcViewer.this, android.R.layout.simple_list_item_1, al);
        listView.setAdapter(listAdapter);
    }
}

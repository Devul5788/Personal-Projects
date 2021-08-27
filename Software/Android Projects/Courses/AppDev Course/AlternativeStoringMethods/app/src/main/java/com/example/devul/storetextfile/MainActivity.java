package com.example.devul.storetextfile;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    EditText ed;
    Button savebtn,showbtn;
    TextView result;
    String h;
    private String filename = "mytest.txt";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ed = (EditText) findViewById(R.id.contenttxt);
        result = (TextView) findViewById(R.id.resulttxt);
        savebtn = (Button) findViewById(R.id.exportbtn);

        savebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                try {
                    // h = DateFormat.format("MM-dd-yyyyy-h-mmssaa", System.currentTimeMillis()).toString();
                    // this will create a new name everytime and unique
                    File root = new File(Environment.getExternalStorageDirectory(), "Notes");

                    if (!root.exists()) {
                        root.mkdirs(); // this will create folder.
                    }
                    File filepath = new File(root, filename);  // file path to save
                    FileWriter writer = new FileWriter(filepath);
                    writer.append(ed.getText().toString());
                    writer.flush();
                    writer.close();
                    String m = filename+" is Saved";
                    result.setText(m);
                    ed.setText("");

                } catch (IOException e) {
                    e.printStackTrace();
                    result.setText(e.getMessage().toString());
                }

            }
        });

        showbtn = (Button) findViewById(R.id.showbtn);
        showbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myData = "";

                try {

                    File root = new File(Environment.getExternalStorageDirectory(), "Notes");
                    File filepath = new File(root,filename);  // file path to save
                    FileReader frdr = null;
                    FileInputStream fis = new FileInputStream(filepath);
                    DataInputStream in = new DataInputStream(fis);
                    BufferedReader br =
                            new BufferedReader(new InputStreamReader(in));
                    String strLine;
                    while ((strLine = br.readLine()) != null) {
                        myData = myData + strLine;
                    }
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    result.setText(e.getMessage().toString());
                }
                ed.setText(myData);
                result.setText(filename+ " is Opened");
            }
        });

    }

}
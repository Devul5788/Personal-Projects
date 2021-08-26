package com.example.devul.storetextfile;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class StoringInObjects extends AppCompatActivity {
    EditText lng,helo_code,helo_no;
    Button btnsv,btnshow;
    String PTH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storing_in_objects);

        lng = (EditText) findViewById(R.id.lang);
        helo_code = (EditText) findViewById(R.id.hello);
        helo_no = (EditText) findViewById(R.id.cardno);

        btnsv=(Button) findViewById(R.id.strButton);
        btnshow=(Button) findViewById(R.id.showButton);

        PTH="/data/data/"+getApplicationContext().getPackageName()+"/storid";

        FileOS.setFile(new File(PTH));

        //// write
        btnsv.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                MyObject obj1 = new MyObject();
                obj1.setHello_code(helo_code.getText().toString());
                obj1.setHello_no(helo_no.getText().toString());
                obj1.setLang(lng.getText().toString());


                if (FileOS.writeFile(obj1)) {
                    Toast.makeText(StoringInObjects.this, "Successfully Registered", Toast.LENGTH_LONG).show();

                }
            }});
        //read
        btnshow.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                try {


                    MyObject obj2 = new MyObject();
                    File f = new File(PTH);
                    if (f.exists())
                    {
                        obj2 = FileOS.readFile();
                        helo_code.setText(obj2.getHello_code());
                        helo_no.setText(obj2.getHello_no());
                        lng.setText(obj2.getLang().toString());
                    }
                } catch (Exception ex) {
                    System.out.println("Error");

                }
            }});

    }
}


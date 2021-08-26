package com.example.devul.sqldatabases;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends Activity {
    Button bt, vt;
    ListView listView;
    ArrayList<String> al = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt = (Button) findViewById(R.id.createDatabase);
        vt = (Button) findViewById(R.id.viewDatabase);
        listView = (ListView) findViewById(R.id.lst);

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                db.addContact(new Phone("Ravi", "9100000000", 1));
                db.addContact(new Phone( "Srinivas", "9199999999", 2));
                db.addContact(new Phone("Tommy", "9522222222", 3));
                db.addContact(new Phone("Karthik", "9533333333", 4));

            }
        });

        vt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHandler db = new DatabaseHandler(MainActivity.this);
                List<Phone> phns = db.getAllContacts();
                for (Phone cn : phns) {
                    String log = "Id: "+cn.getId()+" ,Name: " + cn.getName() + " ,Phone: " + cn.getNumber();
                    al.add(log);
                }
                ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, al);
                listView.setAdapter(listAdapter);
            }
        });


    }
}


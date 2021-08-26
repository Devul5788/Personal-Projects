package com.example.devul.listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class myListView extends AppCompatActivity {

    ListView lv;
    ArrayList<String> al = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myListView);
        lv = (ListView)findViewById(R.id.lst);
        al.add("hello");
        al.add("hello1");
        ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al);
        lv.setAdapter(listAdapter);
    }
}

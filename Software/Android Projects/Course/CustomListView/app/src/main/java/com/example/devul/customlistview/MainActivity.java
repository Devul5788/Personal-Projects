package com.example.devul.customlistview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lst;
    MyAdapter myAdapter;
    ArrayList<MyClass> myClassList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lst = (ListView)findViewById(R.id.list1);

        MyClass my1 = new MyClass("Ajith", R.drawable.prfl, "google.com");
        myClassList.add(my1);

        MyClass my2 = new MyClass("Jaleel", R.drawable.prfl, "hello.com");
        myClassList.add(my2);

        MyClass my3 = new MyClass("TRR", R.drawable.prfl, "java.com");
        myClassList.add(my3);

        MyClass my4 = new MyClass("AAA", R.drawable.prfl, "awesome.com");
        myClassList.add(my4);

        myAdapter = new MyAdapter(MainActivity.this, myClassList);
        lst.setAdapter(myAdapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                MyClass slctd = (MyClass)parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, slctd.getName(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

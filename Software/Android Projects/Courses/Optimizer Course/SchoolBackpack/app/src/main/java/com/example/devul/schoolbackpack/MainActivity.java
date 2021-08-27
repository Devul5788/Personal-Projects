package com.example.devul.schoolbackpack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button bt, vt, lt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt=(Button)findViewById(R.id.bt2);
        vt = (Button)findViewById(R.id.bt3);
        lt = (Button)findViewById(R.id.bt1);
        Typeface face=Typeface.createFromAsset(getAssets(),"Fonts/Other/Vollkorn-Black.ttf");
        lt.setTypeface(face);
        lt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SharedPreferences sp=getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.commit();
            }
        });
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GPACalculator.class));
            }
        });

        vt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GPACalcViewer.class));
            }
        });
    }
}

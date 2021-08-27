package com.example.devul.schoolbackpack;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class RegistrationPage extends AppCompatActivity {
    TextView textView;
    EditText username, password, password2;
    Button register;
    String PTH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        textView = (TextView)findViewById(R.id.title);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        password2 = (EditText)findViewById(R.id.password2);
        register = (Button)findViewById(R.id.register);

        Typeface face = Typeface.createFromAsset(getAssets(),"Fonts/Registrationpage/EraserDust.ttf");
        textView.setTypeface(face);

        PTH="/data/data/"+getApplicationContext().getPackageName()+"/storid";
        FileOS.setFile(new File(PTH));
        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                Object obj1 = new Object();
                if(password.getText().toString().equals(password2.getText().toString())){
                    obj1.setUsername(username.getText().toString());
                    obj1.setPassword(password.getText().toString());
                    if (FileOS.writeFile(obj1)){
                        Toast.makeText(RegistrationPage.this, "Successfully Registered", Toast.LENGTH_LONG).show();

                    }
                }
                else{
                    Toast.makeText(RegistrationPage.this, "Make Sure the passwords " +
                            "entered are the same", Toast.LENGTH_LONG).show();
                }
            }});
    }
}

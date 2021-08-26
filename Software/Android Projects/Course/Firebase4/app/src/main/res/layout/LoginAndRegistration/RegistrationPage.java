package com.example.devul.schoolbackpack.LoginAndRegistration;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devul.schoolbackpack.R;

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
        com.example.devul.schoolbackpack.LoginAndRegistration.FileOS.setFile(new File(PTH));
        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                com.example.devul.schoolbackpack.LoginAndRegistration.Object obj1 = new com.example.devul.schoolbackpack.LoginAndRegistration.Object();
                if(password.getText().toString().equals(password2.getText().toString())){
                    obj1.setUsername(username.getText().toString());
                    obj1.setPassword(password.getText().toString());
                    if (com.example.devul.schoolbackpack.LoginAndRegistration.FileOS.writeFile(obj1)){
                        Toast.makeText(com.example.devul.schoolbackpack.LoginAndRegistration.RegistrationPage.this, "Successfully Registered", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(com.example.devul.schoolbackpack.LoginAndRegistration.RegistrationPage.this, com.example.devul.schoolbackpack.LoginAndRegistration.UserLogin.class));

                    }
                }
                else{
                    Toast.makeText(com.example.devul.schoolbackpack.LoginAndRegistration.RegistrationPage.this, "Make Sure the passwords " +
                            "entered are the same", Toast.LENGTH_LONG).show();
                }
            }});
    }
}

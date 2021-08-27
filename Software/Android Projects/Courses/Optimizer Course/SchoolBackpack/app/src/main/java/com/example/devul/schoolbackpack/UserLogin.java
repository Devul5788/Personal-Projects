package com.example.devul.schoolbackpack;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class UserLogin extends AppCompatActivity {

    SharedPreferences sb;
    String PTH;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        sb=getSharedPreferences("login", MODE_PRIVATE);

        if(sb.contains("username")&&sb.contains("password")){
            startActivity(new Intent(UserLogin.this, MainActivity.class));
        }

        final EditText username, password;
        Button login, register;
        username = (EditText)findViewById(R.id.ed1);
        password = (EditText)findViewById(R.id.ed2);
        login = (Button)findViewById(R.id.bt1);
        register = (Button) findViewById(R.id.bt2);
        PTH = "/data/data/"+getApplicationContext().getPackageName()+"/storid";
        FileOS.setFile(new File(PTH));

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Object obj = new Object();
                    File f = new File(PTH);
                    if (f.exists())
                    {
                        obj = FileOS.readFile();
                    }
                    if ((username.getText().toString().equals(obj.getUsername()) && (password.getText().toString().equals(obj.getPassword()))))
                    {
                        SharedPreferences.Editor e = sb.edit();
                        e.putString("username", obj.getUsername());
                        e.putString("password",obj.getPassword() );
                        e.commit();
                        startActivity(new Intent(UserLogin.this, MainActivity.class));
                        Toast.makeText(UserLogin.this, "Login Successful", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Toast.makeText(UserLogin.this, "Login UnSuccessful", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception ex) {
                    System.out.println("Error");
                }
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserLogin.this, RegistrationPage.class));
            }
        });
}
}
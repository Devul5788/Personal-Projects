package firebasedemo.devul.firebase4;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;
import org.json.JSONObject;
import android.support.v4.app.FragmentManager;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText username, password;
    Button registerButton;
    String user, pass;
    TextView login;
    SharedPreferences sp;
    private DatabaseReference database = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        registerButton = (Button)findViewById(R.id.register);
        login = (TextView)findViewById(R.id.login);

        sp=getSharedPreferences("login",MODE_PRIVATE);

        if(sp.contains("phone") && sp.contains("name"))
        {
            //startActivity(new Intent(MainActivity.this,ProductRecycler.class));
            //finish();
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProductRecycler.class));
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else if(!user.matches("[A-Za-z0-9]+")){
                    username.setError("only alphabet or number allowed");
                }
                else if(user.length()<5){
                    username.setError("at least 5 characters long");
                }
                else if(pass.length()<5){
                    password.setError("at least 5 characters long");
                }
                else {
                    final ProgressDialog pd = new ProgressDialog(MainActivity.this);
                    pd.setMessage("Loading...");
                    pd.show();


                    /////volley started
                    String url = "https://fir-demo-dda93.firebaseio.com/users.json";
                    //String url = "https://androidchatapp-76776.firebaseio.com/users.json";

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {

                            SharedPreferences.Editor sh=sp.edit();
                            sh.putString("phone",pass);
                            sh.putString("name",user);
                            sh.commit();
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if (!obj.has(pass)) {

                                        database.child("users").child(pass).child("name").setValue(user);
                                        database.child("userlist").push().child("name").setValue(user);
                                        database.child("userlist").push().child("phone").setValue(pass);
                                        startActivity(new Intent(MainActivity.this,ProductRecycler.class));
                                        finish();
                                        Toast.makeText(MainActivity.this, "registration successful", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this, "Phone no already exists", Toast.LENGTH_LONG).show();
                                    }

                                } catch (JSONException e) {
                                    //e.printStackTrace();
                                }

                            pd.dismiss();
                        }

                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError );
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(MainActivity.this);
                    rQueue.add(request);
                }//end of volley
            }
        });
    }


}

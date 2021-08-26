package com.example.devul.alertdialog;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alert();
    }

    public void alert(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.activity_main, null);
        dialogBuilder.setView(dialogView);

        EditText edt1 = (EditText) dialogView.findViewById(R.id.edit1);
        EditText edt2 = (EditText) dialogView.findViewById(R.id.edit2);
        EditText edt3 = (EditText) dialogView.findViewById(R.id.edit3);

        dialogBuilder.setTitle("Registration");
        dialogBuilder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int whichButton) {
            //pass

        }
    });

    AlertDialog b = dialogBuilder.create();
        b.show();
    }
}


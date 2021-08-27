package com.devul.GPAMapper.app.ScoresConvertor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.devul.GPAMapper.app.Calculations.GPACalculations;
import com.devul.GPAMapper.app.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class ScoresConvertorViewer extends Fragment {

    TextInputLayout rGrade, tGrade;
    TextView percent;
    SharedPreferences prefs;

    public ScoresConvertorViewer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View myview = inflater.inflate(R.layout.fragment_scores_convertor_viewer, container, false);

        rGrade = myview.findViewById(R.id.rGrade);
        tGrade = myview.findViewById(R.id.tGrade);
        percent = myview.findViewById(R.id.percent);
        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("prefs", MODE_PRIVATE);

        Toolbar toolbar = Objects.requireNonNull(getActivity()).findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Scores Convertor");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        Objects.requireNonNull(rGrade.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validateTotalGrade() | validateRCVDGrade()) {
                    return;

                }
                if (!rGrade.getEditText().getText().toString().trim().isEmpty()) {
                    doCalculations();
                }
            }
        });

        Objects.requireNonNull(tGrade.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validateTotalGrade() | validateRCVDGrade()) {
                    return;
                }
                if (!rGrade.getEditText().getText().toString().trim().isEmpty()) {
                    doCalculations();
                }
            }
        });

        return myview;
    }

    public boolean validateRCVDGrade() {
        String dGrade = rGrade.getEditText().getText().toString().trim();

        if (dGrade.isEmpty()) {
            rGrade.setError("Field Cant Be Empty!");
            return true;
        } else {
            rGrade.setError(null);
            return false;
        }
    }

    public boolean validateTotalGrade() {
        String dGrade = Objects.requireNonNull(tGrade.getEditText()).getText().toString().trim();

        if (dGrade.isEmpty()) {
            tGrade.setError("Field Cant Be Empty!");
            return false;
        } else {
            tGrade.setError(null);
            return true;
        }
    }

    public void doCalculations() {
        if (!Objects.requireNonNull(rGrade.getEditText()).getText().toString().trim().equals("") && !tGrade.getEditText().getText().toString().trim().equals("")) {
            double a = Double.parseDouble(rGrade.getEditText().getText().toString().trim());
            double b = Double.parseDouble(tGrade.getEditText().getText().toString().trim());

            double percent2 = a / b * 100.0;

            String percent3 = "" + GPACalculations.round(percent2, prefs.getInt("decimalPlace", 0)) + "%";
            percent.setText(percent3);
        }
    }
}

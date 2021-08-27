package com.devul.GPAMapper.app.Other;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.devul.GPAMapper.app.Calculations.GPACalculations;
import com.devul.GPAMapper.app.LoginAndRegistration.UserLogin;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Semesters.Semesters;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.devul.GPAMapper.app.Years.Years;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import hotchemi.android.rate.AppRate;

import static android.content.Context.MODE_PRIVATE;

public class MainActivity extends Fragment implements AdapterView.OnItemSelectedListener {

    Button logout;
    TextView currentSemesterPercent, currentSemesterGPA, currentYearPercent, currentYearGPA, hsPercent, hsGPA,
            customSemesterPercent, customSemesterGPA, customYearPercent, customYearGPA, customSemesterTitle, customYearTitle;
    ImageView currentSemesterLG, currentYearLG, hsLG, customSemesterLG, customYearLG;
    double averageSemesterGradeP, averageSemesterGPA, averageYearGradeP, averageYearGPA, averageHSGradeP, averageHSGPA;
    int averageSemesterGradeLG, averageYearGradeLG, averageHSGradeLG;
    ViewFlipper viewFlipper;
    FloatingActionButton refreshButton;

    List<Years> years;
    List<Semesters> semesters;
    List<Semesters> selectedSemesters;
    CardView semesterData, yearData, CV;
    Button customData;
    DatabaseHandler db;
    Years yr;
    Semesters s;

    Spinner year, semester;
    ImageButton p1, p2, p3, n1, n2, n3;
    SharedPreferences prefs;

    public MainActivity() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View myview = inflater.inflate(R.layout.fragment_main_activity, container, false);
        viewFlipper = myview.findViewById(R.id.semesterData);
        logout = myview.findViewById(R.id.logout);
        currentSemesterPercent = myview.findViewById(R.id.currentSemesterPercent);
        currentSemesterLG = myview.findViewById(R.id.currentSemesterLG);
        currentSemesterGPA = myview.findViewById(R.id.currentSemesterGPA);
        currentYearPercent = myview.findViewById(R.id.currentYearPercent);
        currentYearGPA = myview.findViewById(R.id.currentYearGPA);
        currentYearLG = myview.findViewById(R.id.currentYearLG);
        hsPercent = myview.findViewById(R.id.hsPercent);
        hsLG = myview.findViewById(R.id.hsLG);
        hsGPA = myview.findViewById(R.id.hsGPA);
        customSemesterPercent = myview.findViewById(R.id.customSemesterPercent);
        customSemesterLG = myview.findViewById(R.id.customSemesterLG);
        customSemesterGPA = myview.findViewById(R.id.customSemesterGPA);
        customYearPercent = myview.findViewById(R.id.customYearPercent);
        customYearLG = myview.findViewById(R.id.customYearLG);
        customYearGPA = myview.findViewById(R.id.customYearGPA);
        customSemesterTitle = myview.findViewById(R.id.customSemesterTitle);
        customYearTitle = myview.findViewById(R.id.customYearTitle);
        CV = myview.findViewById(R.id.CV);
        refreshButton = myview.findViewById(R.id.refresh);
        prefs = Objects.requireNonNull(getActivity()).getSharedPreferences("prefs", MODE_PRIVATE);

        AppRate.with(getActivity())
                .setInstallDays(1)
                .setLaunchTimes(3)
                .setRemindInterval(2)
                .monitor();

        AppRate.showRateDialogIfMeetsConditions(getActivity());

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar); // get the reference of Toolbar
        toolbar.setTitle("Main Page");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        semesterData = myview.findViewById(R.id.customSemesterData);
        yearData = myview.findViewById(R.id.customYearData);
        customData = myview.findViewById(R.id.customA);
        year = myview.findViewById(R.id.year);
        semester = myview.findViewById(R.id.semester);
        db = new DatabaseHandler(getActivity());
        year.setSelection(1);
        semester.setSelection(1);

        p1 = myview.findViewById(R.id.p1);
        p2 = myview.findViewById(R.id.p2);
        p3 = myview.findViewById(R.id.p3);
        n1 = myview.findViewById(R.id.n1);
        n2 = myview.findViewById(R.id.n2);
        n3 = myview.findViewById(R.id.n3);

        yr = new Years();
        s = new Semesters();

        years = db.getAllYears(false, "", "");
        List<String> yearString = new ArrayList<>();

        for (Years yer : years) {
            yearString.add(yer.getYear());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, yearString);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(dataAdapter);

        year.setOnItemSelectedListener(this);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculations(false);
            }
        });

        customData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CV.setVisibility(CV.isShown() ? View.GONE : View.VISIBLE);
                semesterData.setVisibility(semesterData.isShown() ? View.GONE : View.VISIBLE);
                yearData.setVisibility(yearData.isShown() ? View.GONE : View.VISIBLE);
            }
        });


        p1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousClick();
            }
        });

        p2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousClick();
            }
        });

        p3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousClick();
            }
        });

        n1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClick();
            }
        });

        n2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClick();
            }
        });

        n3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextClick();
            }
        });

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "Fonts/Other/Vollkorn-Black.ttf");
        logout.setTypeface(face);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getActivity().getSharedPreferences("login", MODE_PRIVATE);
                SharedPreferences.Editor e = sp.edit();
                e.clear();
                e.apply();
                startActivity(new Intent(getActivity(), UserLogin.class));
            }
        });

        calculations(false);
        calculations(false);
        calculations(false);

        return myview;
    }

    public void calculations(Boolean customEnabled) {
        averageSemesterGradeP = 0.0;
        averageSemesterGradeLG = 0;
        averageSemesterGPA = 0.0;
        averageYearGradeP = 0.0;
        averageYearGPA = 0.0;
        averageYearGradeLG = 0;
        averageHSGradeP = 0.0;
        averageHSGradeLG = 0;
        averageHSGPA = 0.0;

        db = new DatabaseHandler(getActivity());
        GPACalculations c = new GPACalculations();

        List<Subjects> subjects = db.getAllSubjects(false, "", "");
        List<Semesters> sem = db.getAllSemesters(false, "", "");

        if (subjects.size() > 0) {
            if (!customEnabled) {
                averageSemesterGradeP = c.getAverageSemesterGrade(prefs.getInt("semester", 0), subjects, db, getActivity());
            } else if (customEnabled) {
                averageSemesterGradeP = c.getAverageSemesterGrade(s.getID(), subjects, db, getActivity());
            }

            averageSemesterGradeLG = c.getAverageGradeLG(averageSemesterGradeP, db, getActivity());
            averageSemesterGPA = c.getAverageGPA(averageSemesterGradeLG, db, getActivity());

            if (!customEnabled) {
                if (averageSemesterGradeP > 0) {
                    currentSemesterPercent.setText(GPACalculations.round(averageSemesterGradeP, prefs.getInt("decimalPlace", 0)) + "%");
                    currentSemesterLG.setImageResource(averageSemesterGradeLG);
                    currentSemesterGPA.setText(Double.toString(averageSemesterGPA));
                }
            } else if (customEnabled) {
                if (averageSemesterGradeP > 0) {
                    customSemesterPercent.setText(GPACalculations.round(averageSemesterGradeP, prefs.getInt("decimalPlace", 0)) + "%");
                    customSemesterLG.setImageResource(averageSemesterGradeLG);
                    customSemesterGPA.setText(Double.toString(averageSemesterGPA));
                }
            }
        }
        if (subjects.size() == 0 || averageSemesterGradeP == 0.0) {
            if (!customEnabled) {
                currentSemesterPercent.setText("0.0%");
                currentSemesterLG.setImageResource(R.drawable.no_data_w);
                currentSemesterGPA.setText("0.0");
            } else if (customEnabled) {
                customSemesterPercent.setText("0.0%");
                customSemesterLG.setImageResource(R.drawable.no_data_custom);
                customSemesterGPA.setText("0.0");
            }
        }

        List<Semesters> semesters = db.getAllSemesters(false, "", "");
        List<Subjects> subjects2 = db.getAllSubjects(false, "", "");

        if (semesters.size() > 0) {
            for (Semesters st : semesters) {
                if (!customEnabled) {
                    if (st.getYearID() == prefs.getInt("year", 0) && st.getID() == prefs.getInt("semester", 0)) {
                        Semesters sm = new Semesters(st.getID(), st.getYearID(), st.getSemesterName(),
                                averageSemesterGradeP, averageSemesterGradeLG);
                        db.updateSemester(sm);
                    } else {
                        double num1 = c.getAverageSemesterGrade(st.getID(), subjects2, db, getActivity());
                        int img = c.getAverageGradeLG(num1, db, getActivity());
                        Semesters sm = new Semesters(st.getID(), st.getYearID(), st.getSemesterName(),
                                num1, img);
                        db.updateSemester(sm);
                    }
                } else if (customEnabled) {
                    if (st.getYearID() == yr.getID() && st.getSemesterName().equals(s.getSemesterName())) {
                        Semesters sm = new Semesters(st.getID(), st.getYearID(), st.getSemesterName(),
                                averageSemesterGradeP, averageSemesterGradeLG);
                        db.updateSemester(sm);
                    }
                }
            }

            if (customEnabled) {
                averageYearGradeP = c.getAverageYearGrade(yr.getID(), semesters, db, getActivity());
            } else {
                averageYearGradeP = c.getAverageYearGrade(prefs.getInt("year", 0), semesters, db, getActivity());
            }

            if (averageYearGradeP > 0) {
                averageYearGradeLG = c.getAverageGradeLG(averageYearGradeP, db, getActivity());
                averageYearGPA = c.getAverageGPA(averageYearGradeLG, db, getActivity());

                if (!customEnabled) {
                    currentYearPercent.setText(GPACalculations.round(averageYearGradeP, prefs.getInt("decimalPlace", 0)) + "%");
                    currentYearLG.setImageResource(averageYearGradeLG);
                    currentYearGPA.setText(Double.toString(averageYearGPA));
                } else if (customEnabled) {
                    customYearPercent.setText(GPACalculations.round(averageYearGradeP, prefs.getInt("decimalPlace", 0)) + "%");
                    customYearLG.setImageResource(averageYearGradeLG);
                    customYearGPA.setText(Double.toString(averageYearGPA));
                }
            }
        }

        if (semesters.size() == 0 || averageYearGradeP == 0) {
            if (!customEnabled) {
                currentYearPercent.setText("0.0%");
                currentYearLG.setImageResource(R.drawable.no_data_custom);
                currentYearGPA.setText("0.0");
            } else if (customEnabled) {
                customYearPercent.setText("0.0%");
                customYearLG.setImageResource(R.drawable.no_data_custom);
                customYearGPA.setText("0.0");
            }
        }

        years = db.getAllYears(false, "", "");
        semesters = db.getAllSemesters(false, "", "");

        if (years.size() > 0) {
            for (Years yRS : years) {
                if (yRS.getID() == prefs.getInt("year", 0)) {
                    Years yrs = new Years(prefs.getInt("year", 0), yRS.getYear(), GPACalculations.round(averageYearGradeP, prefs.getInt("decimalPlace", 0)));
                    db.updateYear(yrs);
                } else {
                    double num1 = c.getAverageYearGrade(yRS.getID(), semesters, db, getActivity());
                    Years yrs = new Years(yRS.getID(), yRS.getYear(), GPACalculations.round(num1, prefs.getInt("decimalPlace", 0)));
                    db.updateYear(yrs);
                }
            }
            averageHSGradeP = c.getAverageHSGrade(years, db, getActivity());
        }

        if (years.size() > 0 && averageHSGradeP > 0) {
            averageHSGradeLG = c.getAverageGradeLG(averageHSGradeP, db, getActivity());
            averageHSGPA = c.getAverageGPA(averageHSGradeLG, db, getActivity());

            hsPercent.setText(GPACalculations.round(averageHSGradeP, prefs.getInt("decimalPlace", 0)) + "%");
            hsLG.setImageResource(averageHSGradeLG);
            hsGPA.setText(Double.toString(averageHSGPA));
        } else {
            hsPercent.setText("0.0%");
            hsLG.setImageResource(R.drawable.no_data_custom);
            hsGPA.setText("0.0");
        }
    }

    public void previousClick() {
        viewFlipper.showPrevious();
    }

    public void nextClick() {
        viewFlipper.showNext();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        calculations(false);
        calculations(false);
        calculations(false);

        switch (adapterView.getId()) {
            case R.id.year:
                year.setSelection(i);

                customYearTitle.setText("Year: " + year.getSelectedItem().toString());

                semesters = db.getAllSemesters(false, "", "");
                years = db.getAllYears(false, "", "");

                for (Years yrs : years) {
                    if (yrs.getYear().equals(year.getSelectedItem().toString())) {
                        yr = yrs;
                    }
                }

                selectedSemesters = new ArrayList<Semesters>();
                List<String> semestersString = new ArrayList<String>();

                for (Semesters sm : semesters) {
                    if (sm.getYearID() == yr.getID()) {
                        semestersString.add(sm.getSemesterName());
                        selectedSemesters.add(sm);
                    }
                }

                ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, semestersString);

                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                semester.setAdapter(dataAdapter3);
                semester.setOnItemSelectedListener(this);
                break;
            case R.id.semester:
                semester.setSelection(i);
                customSemesterTitle.setText("Semester: " + semester.getSelectedItem().toString());

                for (Semesters se : selectedSemesters) {
                    if (se.getSemesterName().equals(semester.getSelectedItem().toString())) {
                        s = se;
                    }
                }

                calculations(true);
                calculations(true);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
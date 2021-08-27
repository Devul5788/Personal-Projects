package com.devul.GPAMapper.app.Subjects;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Semesters.Semesters;
import com.devul.GPAMapper.app.Years.Years;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class SubjectsGraphView extends Fragment {

    BarChart barChart;
    DatabaseHandler db;
    Spinner year, semester;
    Years yr;
    Semesters sm;
    List<Years> years;
    List<Semesters> semesters;
    List<Semesters> selectedSemesters;
    private AdView mAdView;

    public SubjectsGraphView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_subjects_graph_viewer, container, false);
        barChart = myView.findViewById(R.id.barchart);
        year = myView.findViewById(R.id.year);
        semester = myView.findViewById(R.id.semester);
        db = new DatabaseHandler(getActivity());

        yr = new Years();
        sm = new Semesters();

        years = db.getAllYears(false, "", "");
        List<String> yearString = new ArrayList<>();

        MobileAds.initialize(getActivity(), "ca-app-pub-2700801363223485~8825163774");

        mAdView = myView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        for (Years yer : years) {
            yearString.add(yer.getYear());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, yearString);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(dataAdapter);

        years = db.getAllYears(false, "", "");
        semesters = db.getAllSemesters(false, "", "");

        setGraph();

        year.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                year.setSelection(i);

                semesters = db.getAllSemesters(false, "", "");
                years = db.getAllYears(false, "", "");

                for (Years yrs : years) {
                    if (yrs.getYear().equals(year.getSelectedItem().toString())) {
                        yr = yrs;
                    }
                }

                selectedSemesters = new ArrayList<>();
                List<String> semestersString = new ArrayList<>();

                for (Semesters sm : semesters) {
                    if (sm.getYearID() == yr.getID()) {
                        semestersString.add(sm.getSemesterName());
                        selectedSemesters.add(sm);
                    }
                }

                ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, semestersString);

                dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                semester.setAdapter(dataAdapter3);
                semester.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        semester.setSelection(i);

                        for (Semesters se : selectedSemesters) {
                            if (se.getSemesterName().equals(semester.getSelectedItem().toString())) {
                                sm = se;
                            }
                        }

                        setGraph();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        setGraph();

        return myView;
    }

    private void setData(List<Subjects> subjects) {
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 1; i <= subjects.size(); i++) {
            float value = (float) subjects.get(i - 1).getGradeP();
            yVals.add(new BarEntry(i, value));
        }

        BarDataSet set = new BarDataSet(yVals, "Assignments");

        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);

        data.setValueTextSize(15f);
        data.setBarWidth(0.9f);
        barChart.setData(data);
        barChart.invalidate();
        barChart.animateY(500);
    }

    public void setGraph() {
        List<Subjects> subjects = db.getAllSubjects(false, "", "");
        List<Subjects> list = new ArrayList<>();
        List<Semesters> sems = db.getAllSemesters(false, "", "");

        for (Subjects s : subjects) {
            if (sm.getSemesterName() != null) {
                if (s.getSemesterID() == sm.getID() && s.getGradeP() > 0) {
                    list.add(s);
                }
            } else {
                if (s.getSemesterID() == sems.get(0).getID() && s.getGradeP() > 0) {
                    list.add(s);
                }
            }
        }

        barChart.getDescription().setEnabled(false);

        setData(list);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(1);

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setLabelCount(15, false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMaximum(109f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setEnabled(false);
        barChart.setFitBars(true);
        barChart.setPinchZoom(true);
        barChart.setHorizontalScrollBarEnabled(true);
        barChart.setVisibleXRangeMaximum(3);
        barChart.moveViewToX(1);
    }
}

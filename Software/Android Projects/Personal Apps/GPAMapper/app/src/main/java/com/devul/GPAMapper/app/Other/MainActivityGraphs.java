package com.devul.GPAMapper.app.Other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class MainActivityGraphs extends Fragment {

    BarChart yearChart, semChart;
    DatabaseHandler db;
    private AdView mAdView;

    public MainActivityGraphs() {
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
        View myview = inflater.inflate(R.layout.fragment_main_activity_graphs, container, false);

        yearChart = myview.findViewById(R.id.barchart2);
        semChart = myview.findViewById(R.id.barchart);
        db = new DatabaseHandler(getActivity());
        List<Semesters> sems = db.getAllSemesters(false, "", "");
        List<Years> years = db.getAllYears(false, "", "");

        MobileAds.initialize(getActivity(), "ca-app-pub-2700801363223485~8825163774");

        mAdView = myview.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        yearChart.getDescription().setEnabled(false);
        semChart.getDescription().setEnabled(false);

        setDataS(sems);
        setDataY(years);
        semChart.setDrawGridBackground(false);
        yearChart.setDrawGridBackground(false);

        XAxis xAxis = semChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(1);

        XAxis xAxis2 = yearChart.getXAxis();
        xAxis2.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis2.setDrawGridLines(false);
        xAxis2.setLabelCount(1);

        YAxis leftAxis = semChart.getAxisLeft();
        leftAxis.setLabelCount(15, false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMaximum(109f);
        leftAxis.setAxisMinimum(0f);

        YAxis leftAxis2 = yearChart.getAxisLeft();
        leftAxis2.setLabelCount(15, false);
        leftAxis2.setSpaceTop(15f);
        leftAxis2.setAxisMaximum(109f);
        leftAxis2.setAxisMinimum(0f);

        YAxis rightAxis = semChart.getAxisRight();
        rightAxis.setEnabled(false);
        semChart.setFitBars(true);
        semChart.setPinchZoom(true);
        semChart.setHorizontalScrollBarEnabled(true);
        semChart.setVisibleXRangeMaximum(3);
        semChart.moveViewToX(1);

        YAxis rightAxis2 = yearChart.getAxisRight();
        rightAxis2.setEnabled(false);
        yearChart.setFitBars(true);
        yearChart.setPinchZoom(true);
        yearChart.setHorizontalScrollBarEnabled(true);
        yearChart.setVisibleXRangeMaximum(3);
        yearChart.moveViewToX(1);

        return myview;
    }

    private void setDataS(List<Semesters> semesters) {
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 1; i <= semesters.size(); i++) {
            float value = (float) semesters.get(i - 1).getPercent();
            yVals.add(new BarEntry(i, value));
        }

        BarDataSet set = new BarDataSet(yVals, "Semesters");

        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);

        data.setValueTextSize(15f);
        data.setBarWidth(0.9f);
        semChart.setData(data);
        semChart.invalidate();
        semChart.animateY(500);
    }

    private void setDataY(List<Years> years) {
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 1; i <= years.size(); i++) {
            float value = (float) years.get(i - 1).getPercent();
            yVals.add(new BarEntry(i, value));
        }

        BarDataSet set = new BarDataSet(yVals, "Years");

        set.setColors(ColorTemplate.MATERIAL_COLORS);
        set.setDrawValues(true);

        BarData data = new BarData(set);

        data.setValueTextSize(15f);
        data.setBarWidth(0.9f);
        yearChart.setData(data);
        yearChart.invalidate();
        yearChart.animateY(500);
    }
}

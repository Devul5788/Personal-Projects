package com.devul.GPAMapper.app.Assignments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class AssignmentGraphView extends Fragment {

    private static final String TAG = "MainActivity";
    LineChart lineChart;
    DatabaseHandler db;
    Subjects subject;
    private AdView mAdView;

    public AssignmentGraphView() {
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
        View myView = inflater.inflate(R.layout.fragment_assignment_graph_view, container, false);

        lineChart = myView.findViewById(R.id.lineChart);
        db = new DatabaseHandler(getActivity());
        subject = getActivity().getIntent().getParcelableExtra("subject");

        MobileAds.initialize(getActivity(), "ca-app-pub-2700801363223485~8825163774");

        mAdView = myView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        List<Assignments> assignments = db.getAllAssignments(false, "", "");
        List<Assignments> list = new ArrayList<>();

        for (Assignments as : assignments) {
            if (as.getSubjectID() == subject.getID()) {
                list.add(as);
            }
        }

        lineChart.getDescription().setEnabled(false);
        lineChart.setDragEnabled(true);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(1);

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setLabelCount(15, false);
        leftAxis.setSpaceTop(15f);
        leftAxis.setAxisMaximum(109f);
        leftAxis.setAxisMinimum(0f);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
        lineChart.fitScreen();

        setData(list);
        return myView;
    }

    private void setData(List<Assignments> assignments) {
        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 1; i <= assignments.size(); i++) {
            float value = (float) assignments.get(i - 1).getScore();
            yVals.add(new Entry(i, value));
        }

        LineDataSet set1 = new LineDataSet(yVals, "Assignments");
        set1.setFillAlpha(110);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();

        dataSets.add(set1);

        LineData data = new LineData(dataSets);

        set1.setDrawFilled(true);
        if (Utils.getSDKInt() >= 18) {
            // fill drawable only supported on api level 18 and above
            Drawable drawable = ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.fade_red);
            set1.setFillDrawable(drawable);
        } else {
            set1.setFillColor(Color.BLACK);
        }

        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        set1.setLineWidth(3f);
        set1.setDrawValues(true);
        lineChart.setData(data);
        data.setValueTextSize(15f);
        lineChart.invalidate();
        lineChart.animateY(500);
    }
}

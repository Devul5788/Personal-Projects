package com.devul.GPAMapper.app.Categories;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Subjects.Subjects;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class CategoryGraphView extends Fragment {

    PieChart pieChart;
    DatabaseHandler db;
    Subjects subject;
    private AdView mAdView;

    public CategoryGraphView() {
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
        final View myView = inflater.inflate(R.layout.fragment_category_graph_view, container, false);

        pieChart = myView.findViewById(R.id.pieChart);
        db = new DatabaseHandler(getActivity());
        subject = getActivity().getIntent().getParcelableExtra("subject");

        MobileAds.initialize(getActivity(), "ca-app-pub-2700801363223485~8825163774");

        mAdView = myView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(true);
        pieChart.setExtraOffsets(5, 10, 5, 10);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);
        ArrayList<PieEntry> yValues = new ArrayList<>();
        List<Categories> categories = db.getAllCategories();

        if (categories.size() != 0) {
            for (Categories c : categories) {
                if (c.getSubjectID() == subject.getID() && c.getPercentWeightage() >= 0) {
                    yValues.add(new PieEntry((float) c.getPercentWeightage(), c.getCatName()));
                }
            }
        }

        pieChart.animateY(1000, Easing.EasingOption.EaseInOutCubic);

        Description description = new Description();
        description.setText("");
        pieChart.setDescription(description);

        PieDataSet dataSet = new PieDataSet(yValues, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);
        pieChart.setData(data);

        pieChart.setCenterText("Categories");
        return myView;
    }
}

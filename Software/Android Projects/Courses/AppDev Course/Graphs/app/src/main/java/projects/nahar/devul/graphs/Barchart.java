package projects.nahar.devul.graphs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Barchart extends AppCompatActivity {

    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        barChart = (BarChart) findViewById(R.id.barchart);

        barChart.getDescription().setEnabled(false);

        setData(50);
        barChart.setDrawGridBackground(false);

        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

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
        barChart.setVisibleXRangeMaximum(8);
        barChart.moveViewToX(10);
    }

    private void setData(int count){
        ArrayList<BarEntry> yVals = new ArrayList<>();

        for (int i = 1; i<=count; i++){
            float value = (float) (Math.random()*100);
            yVals.add(new BarEntry(i, (int) value));
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
}

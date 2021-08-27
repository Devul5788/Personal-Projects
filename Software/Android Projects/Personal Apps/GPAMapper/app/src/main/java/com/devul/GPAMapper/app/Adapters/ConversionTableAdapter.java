package com.devul.GPAMapper.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devul.GPAMapper.app.ConversionTables.Conversions;
import com.devul.GPAMapper.app.R;

import java.util.ArrayList;

public class ConversionTableAdapter extends BaseAdapter {

    private Context c;
    private ArrayList<Conversions> conversionList;

    public ConversionTableAdapter(Context ctx, ArrayList<Conversions> conversionList) {
        this.c = ctx;
        this.conversionList = conversionList;
    }

    @Override
    public int getCount() {
        return conversionList.size();
    }

    @Override
    public Object getItem(int pos) {
        return conversionList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return conversionList.indexOf(getItem(pos));
    }


    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.conversion_table_list_row, null);
        }

        ImageView grade = convertView.findViewById(R.id.grade);

        TextView percent = convertView.findViewById(R.id.percent);

        TextView GPA = convertView.findViewById(R.id.GPA);

        //SET DATA TO THEM
        grade.setImageResource(conversionList.get(pos).getLetterGrade());
        percent.setText(conversionList.get(pos).getPercentage());
        GPA.setText(Double.toString(conversionList.get(pos).getGPA()));
        return convertView;
    }
}

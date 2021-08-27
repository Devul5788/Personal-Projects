package com.devul.GPAMapper.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devul.GPAMapper.app.Calculations.GPACalculations;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Semesters.Semesters;
import com.devul.GPAMapper.app.Years.Years;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class YearAndSemesterAdapter extends RecyclerView.Adapter<YearAndSemesterAdapter.MyViewHolder> {


    private Context con;
    private ArrayList<Years> yearsList;//this for normal data in listview
    private ArrayList<Semesters> semestersList;//this for normal data in listview
    private Boolean yearEnabled;
    private DatabaseHandler db;

    public YearAndSemesterAdapter(Context con, Boolean yearEnabled, ArrayList<Years> yearsList, DatabaseHandler db) {
        this.con = con;
        this.yearEnabled = yearEnabled;
        this.yearsList = yearsList;
        this.db = db;
    }

    public YearAndSemesterAdapter(Context c, String s, Boolean yearEnabled, ArrayList<Semesters> semestersList, DatabaseHandler db) {
        this.con = c;
        this.yearEnabled = yearEnabled;
        this.semestersList = semestersList;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.year_semester_list_row, parent, false);
        MyViewHolder svh = new MyViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (yearEnabled == true) {
            Years y = yearsList.get(position);

            GPACalculations c = new GPACalculations();
            int grade = c.getAverageGradeLG(y.getPercent(), db, con);

            holder.name.setText(y.getYear());

            if (y.getPercent() > 0) {
                holder.averageGrade.setImageResource(grade);
            } else {
                holder.averageGrade.setImageResource(R.drawable.no_data_b);
            }
        } else {
            Semesters s = semestersList.get(position);
            holder.name.setText(s.getSemesterName());
            if (s.getPercent() > 0) {
                holder.averageGrade.setImageResource(s.getGrade());
            } else {
                holder.averageGrade.setImageResource(R.drawable.no_data_b);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (yearEnabled == true) {
            return yearsList.size();
        } else {
            return semestersList.size();
        }
    }

    public void removeItem(int position) {
        if (yearEnabled == true) {
            yearsList.remove(position);
        } else {
            semestersList.remove(position);
        }
        notifyItemRemoved(position);
    }

    public Years getItemY(int position) {
        return yearsList.get(position);
    }

    public Semesters getItemS(int position) {
        return semestersList.get(position);
    }

    public void restoreItemY(Years item, int position) {
        yearsList.add(position, item);
        notifyItemInserted(position);
    }

    public void restoreItemS(Semesters item, int position) {
        semestersList.add(position, item);
        notifyItemInserted(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView averageGrade;
        public TextView name;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.ysName);
            averageGrade = itemView.findViewById(R.id.ys_grade);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

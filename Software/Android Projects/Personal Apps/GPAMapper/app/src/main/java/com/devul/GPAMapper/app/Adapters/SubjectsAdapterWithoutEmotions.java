package com.devul.GPAMapper.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Subjects.Subjects;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectsAdapterWithoutEmotions extends RecyclerView.Adapter<SubjectsAdapterWithoutEmotions.MyViewHolder2> {

    private Context c;
    private ArrayList<Subjects> subjectsList;//this for normal data in listview
    private int img;
    private DatabaseHandler db;

    public SubjectsAdapterWithoutEmotions(Context c, ArrayList<Subjects> subjectsList, DatabaseHandler db) {
        this.c = c;
        this.subjectsList = subjectsList;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_row_without_emotion, parent, false);
        return new MyViewHolder2(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder2 holder, int position) {
        holder.name.setText(subjectsList.get(position).getSubject());
        holder.averageGrade.setImageResource(subjectsList.get(position).getGrade());
    }

    @Override
    public int getItemCount() {
        return subjectsList.size();
    }

    public void removeItem(int position) {
        subjectsList.remove(position);
        notifyItemRemoved(position);
    }

    public Subjects getItem(int position) {
        return subjectsList.get(position);
    }

    public void restoreItem(Subjects item, int position) {
        subjectsList.add(position, item);
        notifyItemInserted(position);
    }

    public static class MyViewHolder2 extends RecyclerView.ViewHolder {

        public ImageView averageGrade;
        public TextView name;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder2(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.subjectName2);
            averageGrade = itemView.findViewById(R.id.average_grade2);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

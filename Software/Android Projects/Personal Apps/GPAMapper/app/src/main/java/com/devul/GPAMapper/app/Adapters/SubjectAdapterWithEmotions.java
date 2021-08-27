package com.devul.GPAMapper.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devul.GPAMapper.app.Feelings.Feelings;
import com.devul.GPAMapper.app.Other.DataInitializer;
import com.devul.GPAMapper.app.Other.DatabaseHandler;
import com.devul.GPAMapper.app.R;
import com.devul.GPAMapper.app.Subjects.Subjects;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubjectAdapterWithEmotions extends RecyclerView.Adapter<SubjectAdapterWithEmotions.MyViewHolder> {

    private Context c;
    private ArrayList<Subjects> subjectsList;//this for normal data in listview
    private int img;
    private DatabaseHandler db;

    public SubjectAdapterWithEmotions(Context c, ArrayList<Subjects> subjectsList, DatabaseHandler db) {
        this.c = c;
        this.subjectsList = subjectsList;
        this.db = db;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_list_row_emotion, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Subjects s = subjectsList.get(position);

        if (DataInitializer.emojiState) {
            List<Feelings> feelingsList = db.getAllFeelings();

            int num = s.getAverageFeelingNumber();

            if (num > 0) {
                for (Feelings f : feelingsList) {
                    if (f.getNum() == num) {
                        img = f.getImg();
                    }
                }
                holder.feeling.setVisibility(View.VISIBLE);
                holder.feeling.setImageResource(img);
                holder.name.setText(s.getSubject());
                holder.averageGrade.setImageResource(s.getGrade());
            } else {
                holder.feeling.setVisibility(View.VISIBLE);
                holder.name.setText(s.getSubject());
                holder.averageGrade.setImageResource(s.getGrade());
            }
        } else {
            holder.name.setText(s.getSubject());
            holder.averageGrade.setImageResource(s.getGrade());
        }
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView feeling, averageGrade;
        public TextView name;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            feeling = itemView.findViewById(R.id.list_image);
            name = itemView.findViewById(R.id.subjectName1);
            averageGrade = itemView.findViewById(R.id.average_grade);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

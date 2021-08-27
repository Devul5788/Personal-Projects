package com.devul.GPAMapper.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devul.GPAMapper.app.Assignments.Assignments;
import com.devul.GPAMapper.app.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignmentsAdapter extends RecyclerView.Adapter<AssignmentsAdapter.MyViewHolder> {

    private Context c;
    private ArrayList<Assignments> assignmentList;
    private int a;

    public AssignmentsAdapter(Context ctx, ArrayList<Assignments> assignmentList) {
        this.c = ctx;
        this.assignmentList = assignmentList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_list_row, parent, false);
        MyViewHolder svh = new MyViewHolder(v);
        return svh;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.category.setImageResource(assignmentList.get(position).getCategoryImg());
        holder.name.setText(assignmentList.get(position).getAssignmentName());
        holder.date.setText(assignmentList.get(position).getDate());
        holder.grade.setImageResource(assignmentList.get(position).getGradeImg());
    }

    @Override
    public int getItemCount() {
        return assignmentList.size();
    }

    public void removeItem(int position) {
        assignmentList.remove(position);
        notifyItemRemoved(position);
    }

    public Assignments getItem(int position) {
        return assignmentList.get(position);
    }

    public void restoreItem(Assignments item, int position) {
        assignmentList.add(position, item);
        notifyItemInserted(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView category, grade;
        public TextView name, date;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category);
            name = itemView.findViewById(R.id.assignmentName1);
            date = itemView.findViewById(R.id.date);
            grade = itemView.findViewById(R.id.average_grade);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

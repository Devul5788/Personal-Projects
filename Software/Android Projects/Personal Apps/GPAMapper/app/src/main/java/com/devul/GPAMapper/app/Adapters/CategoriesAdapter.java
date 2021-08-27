package com.devul.GPAMapper.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.R;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.MyViewHolder> {

    private Context c;
    private ArrayList<Categories> categoriesList;


    public CategoriesAdapter(Context ctx, ArrayList<Categories> categoriesList) {
        this.c = ctx;
        this.categoriesList = categoriesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_list_row, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.category.setImageResource(categoriesList.get(position).getCategoryImg());
        holder.name.setText(categoriesList.get(position).getCatName());
        holder.percent.setText(Integer.toString(categoriesList.get(position).getPercentWeightage()));
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public void removeItem(int position) {
        categoriesList.remove(position);
        notifyItemRemoved(position);
    }

    public Categories getItem(int position) {
        return categoriesList.get(position);
    }

    public void restoreItem(Categories item, int position) {
        categoriesList.add(position, item);
        notifyItemInserted(position);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public ImageView category;
        public TextView name, percent;
        public RelativeLayout viewBackground, viewForeground;

        public MyViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.Category_LR);
            name = itemView.findViewById(R.id.CategoryName12);
            percent = itemView.findViewById(R.id.PercentWeightage12);
            viewBackground = itemView.findViewById(R.id.view_background);
            viewForeground = itemView.findViewById(R.id.view_foreground);
        }
    }
}

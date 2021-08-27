package com.devul.GPAMapper.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.devul.GPAMapper.app.Categories.Categories;
import com.devul.GPAMapper.app.R;

import java.util.ArrayList;

public class CategoryListAdapter extends BaseAdapter {

    private Context c;
    private ArrayList<Categories> categoriesList;

    public CategoryListAdapter(Context ctx, ArrayList<Categories> categoriesList) {
        this.c = ctx;
        this.categoriesList = categoriesList;
    }

    @Override
    public int getCount() {
        return categoriesList.size();
    }

    @Override
    public Object getItem(int pos) {
        return categoriesList.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return categoriesList.indexOf(getItem(pos));
    }


    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.category_tags_list_row, null);
        }

        ImageView category = convertView.findViewById(R.id.Category_123);

        TextView name = convertView.findViewById(R.id.CategoryName_123);

        //SET DATA TO THEM
        category.setImageResource(categoriesList.get(pos).getCategoryImg());
        name.setText(categoriesList.get(pos).getCatName());

        return convertView;
    }
}

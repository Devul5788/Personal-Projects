package com.example.devul.customlistview;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter{

    Context c;
    ArrayList<MyClass> mylist;//this for normal data in listview
    ArrayList<MyClass> filterList;//filtered data in list view

    public MyAdapter(Context ctx, ArrayList<MyClass> mylist1) {
        // TODO Auto-generated constructor stub
        this.c=ctx;
        this.mylist=mylist1;
        this.filterList=mylist1;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mylist.size();
    }
    @Override
    public Object getItem(int pos) {
        // TODO Auto-generated method stub
        return mylist.get(pos);
    }
    @Override
    public long getItemId(int pos) {
        // TODO Auto-generated method stub
        return mylist.indexOf(getItem(pos));
    }
    @Override
    public View getView(int pos, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        LayoutInflater inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_row, null);
        }

        TextView nameTxt=(TextView) convertView.findViewById(R.id.title1);

        ImageView img=(ImageView) convertView.findViewById(R.id.list_image);

        TextView src = (TextView) convertView.findViewById(R.id.phnlst);
        //SET DATA TO THEM
        nameTxt.setText(mylist.get(pos).getName());
        img.setImageResource(mylist.get(pos).getImg());
        src.setText(mylist.get(pos).getSrc());

        return convertView;
    }
}
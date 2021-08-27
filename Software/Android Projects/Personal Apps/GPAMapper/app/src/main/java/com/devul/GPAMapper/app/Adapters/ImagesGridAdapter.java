package com.devul.GPAMapper.app.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.devul.GPAMapper.app.R;

public class ImagesGridAdapter extends BaseAdapter {

    private final int[] gridViewImageId;
    private Context mContext;

    public ImagesGridAdapter(Context context, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
    }

    @Override
    public int getCount() {
        return gridViewImageId.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            gridViewAndroid = inflater.inflate(R.layout.images_grid_row, null);
            ImageView imageViewAndroid = gridViewAndroid.findViewById(R.id.android_gridview_image);
            imageViewAndroid.setImageResource(gridViewImageId[i]);
        } else {
            gridViewAndroid = convertView;
        }

        return gridViewAndroid;
    }
}

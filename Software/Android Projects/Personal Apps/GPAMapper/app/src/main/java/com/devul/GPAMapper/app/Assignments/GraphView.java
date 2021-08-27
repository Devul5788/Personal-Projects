package com.devul.GPAMapper.app.Assignments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.devul.GPAMapper.app.R;

import androidx.fragment.app.Fragment;

public class GraphView extends Fragment {

    public GraphView() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_graph_view, container, false);

        return myView;
    }
}

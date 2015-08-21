package com.github.koooe.ganke.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.koooe.ganke.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class BlankFragment extends Fragment {

    private static final String ARG_CATEGORY = "category";
    @Bind(R.id.tv_category)
    TextView categoryTextView;
    private String category;

    public BlankFragment() {
        // Required empty public constructor
    }

    public static BlankFragment newInstance(String category) {
        BlankFragment fragment = new BlankFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY);
        } else {
            throw new IllegalArgumentException("need category");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        categoryTextView.setText(category);
    }
}

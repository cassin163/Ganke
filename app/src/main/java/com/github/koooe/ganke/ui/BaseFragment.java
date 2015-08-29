package com.github.koooe.ganke.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.github.koooe.ganke.bean.DayData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <b>kassadin@foxmail.com</b> on 15/8/30 01:14
 */
public abstract class BaseFragment extends Fragment {

    public static final String ARG_CATEGORY = "category";

    protected String category;
    protected List<DayData> dayDatas = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category = getArguments().getString(BaseListFragment.ARG_CATEGORY);
        }
    }
}

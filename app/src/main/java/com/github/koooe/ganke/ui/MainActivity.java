package com.github.koooe.ganke.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;

import com.github.koooe.ganke.R;
import com.github.koooe.ganke.api.Api;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ToolbarActivity {

    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    @Override
    protected int setLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        for (String category : Api.categories) {
            tabLayout.addTab(tabLayout.newTab().setText(category));
        }
    }

}

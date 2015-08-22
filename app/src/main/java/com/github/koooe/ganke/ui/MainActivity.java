package com.github.koooe.ganke.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.koooe.ganke.R;
import com.github.koooe.ganke.api.Api;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ToolbarActivity {

    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    FragmentManager fm;
    private MainFragmentPagerAdapter mainAdapter;

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

        fm = getSupportFragmentManager();

        for (int i = 0; i < Api.categories.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(Api.categories[i]));
        }

        mainAdapter = new MainFragmentPagerAdapter(fm, Api.categories.length);
        viewPager.setAdapter(mainAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    static class MainFragmentPagerAdapter extends FragmentStatePagerAdapter {

        int size;

        public MainFragmentPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            this.size = size;
        }

        @Override
        public Fragment getItem(int position) {

            return BaseFragment.newInstance(Api.categories[position]);
        }

        @Override
        public int getCount() {
            return size;
        }
    }
}

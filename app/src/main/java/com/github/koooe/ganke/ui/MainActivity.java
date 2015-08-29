package com.github.koooe.ganke.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.github.koooe.ganke.R;
import com.github.koooe.ganke.api.Api;
import com.github.koooe.ganke.util.DebugLog;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends ToolbarActivity {

    @Bind(R.id.tablayout)
    TabLayout tabLayout;

    @Bind(R.id.viewPager)
    ViewPager viewPager;

    FragmentManager fm;

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

        MainFragmentPagerAdapter mainAdapter = new MainFragmentPagerAdapter(fm, Api.categories);
        viewPager.setAdapter(mainAdapter);
        viewPager.setOffscreenPageLimit(Api.categories.length - 1);

        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public void onBackPressed() {
        if (doubleClickToExit()) {
            super.onBackPressed();
        }
    }

    long exitTime = 0;

    private boolean doubleClickToExit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            String applicationName = getResources().getString(
                    R.string.app_name);
            String msg = "再按一次返回键退出" + applicationName;
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
            return false;
        } else {
           return true;
        }
    }


    static class MainFragmentPagerAdapter extends FragmentPagerAdapter {

        String[] categories;

        public MainFragmentPagerAdapter(FragmentManager fm, String[] categories) {
            super(fm);
            this.categories = categories;
        }

        @Override
        public Fragment getItem(int position) {
            DebugLog.d(categories[position]);
            return BaseFragment.newInstance(categories[position]);
        }

        @Override
        public int getCount() {
            return categories.length;
        }
    }
}

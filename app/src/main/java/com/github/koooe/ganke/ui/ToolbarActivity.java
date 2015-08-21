package com.github.koooe.ganke.ui;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.github.koooe.ganke.R;

/**
 * 基类处理下toolbar
 * <p/>
 * Created by <b>kassadin@foxmail.com</b> on 15/8/21 21:24
 */
public abstract class ToolbarActivity extends AppCompatActivity {

    public Toolbar mToolbar;
    public ActionBar mActionBar;
    public AppBarLayout mAppBar;
    public TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResId());

        mAppBar = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbarTitle = (TextView) findViewById(R.id.toolbar_title);

        if (mToolbar == null || mAppBar == null) {
            throw new IllegalStateException("No toolbar");
        }

        setSupportActionBar(mToolbar);

        if (mToolbarTitle != null) {
            if (mActionBar != null) mActionBar.setDisplayShowTitleEnabled(false);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            mAppBar.setElevation(10.6f);
        }
    }

    @Override
    protected void onTitleChanged(CharSequence title, int color) {
        super.onTitleChanged(title, color);
        if (mToolbarTitle != null) {
            mToolbarTitle.setText(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected abstract int setLayoutResId();

}

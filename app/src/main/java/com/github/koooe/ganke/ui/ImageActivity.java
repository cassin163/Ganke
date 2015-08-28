package com.github.koooe.ganke.ui;

import android.os.Bundle;

import com.github.koooe.ganke.R;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/8/29.
 */
public class ImageActivity extends ToolbarActivity {

    @Bind(R.id.imageView)
    ImageView imageview;

    public ImageActivity(){}

    @Override
    protected int setLayoutResId() {return R.layout.activity_imageview;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}

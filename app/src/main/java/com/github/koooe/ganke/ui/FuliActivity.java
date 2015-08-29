package com.github.koooe.ganke.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.github.koooe.ganke.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;

/**
 * Created by finally_xiaojian@163.com on 2015/8/30.
 *  this Activity is load image is zoom
 */
public class FuliActivity extends ToolbarActivity  {

    public static String url;
    public static final String IMAGE_URL="url";

    @Bind(R.id.iview)
    ImageViewTouch imageView;
    ImageLoader imageLoader=ImageLoader.getInstance();

    public FuliActivity(){}
    @Override
    protected int setLayoutResId() {
        return R.layout.activity_imagevice;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        parseIntent(getIntent());
        imageView.setDisplayType(ImageViewTouchBase.DisplayType.FIT_IF_BIGGER);
        if(TextUtils.isEmpty(url)){
            finish();
        }
        imageLoader.displayImage(url,imageView);
    }

    public void parseIntent(Intent intent){
        if(getIntent() != null){
            url=getIntent().getStringExtra(IMAGE_URL);
        }
    }

}

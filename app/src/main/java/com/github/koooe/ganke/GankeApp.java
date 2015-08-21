package com.github.koooe.ganke;

import android.app.Application;

/**
 * Created by <b>kassadin@foxmail.com</b> on 15/8/21 21:38
 */
public class GankeApp extends Application {

    private static GankeApp INSTANCE;

    public static GankeApp getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}

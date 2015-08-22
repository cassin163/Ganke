package com.github.koooe.ganke.api;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.github.koooe.ganke.GankeApp;
import com.github.koooe.ganke.util.DebugLog;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by <b>kassadin@foxmail.com</b> on 15/8/21 22:32
 */
public class Api {

    public static final String[] categories = {"Android", "iOS",
            "前端", "拓展资源","福利", "休息视频"};

    public static final int PAGE_ZISE = 10;
    public static final String GANK_DATA = "http://gank.avosapps.com/api/data/%s/" + PAGE_ZISE + "/%d";
    public static final String GANK_DAY = "http://gank.avosapps.com/api/day/2015/08/06";
    public static final String GANK_RAMDOM = "http://gank.avosapps.com/api/random/data/Android/20";


    public static void getData(String category, int page,  Response.Listener<String> listener,
                               Response.ErrorListener errorListener){
        try {
            category = URLEncoder.encode(category, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = String.format(GANK_DATA, category, page);
        DebugLog.d(url);
        StringRequest request = new StringRequest(url, listener, errorListener);
        GankeApp.getRequestQueue().add(request);

    }

}

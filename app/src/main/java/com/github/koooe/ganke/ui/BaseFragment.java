package com.github.koooe.ganke.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.koooe.ganke.R;
import com.github.koooe.ganke.api.Api;
import com.github.koooe.ganke.bean.BaseResponse;
import com.github.koooe.ganke.bean.DayData;
import com.github.koooe.ganke.util.DebugLog;
import com.google.gson.Gson;
import com.markmao.pulltorefresh.widget.XListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ganke.adapter.BaseAdapterHelper;
import ganke.adapter.QuickAdapter;

public class BaseFragment extends Fragment implements XListView.IXListViewListener {

    private static final String ARG_CATEGORY = "category";
    public QuickAdapter<DayData> adapter;
    int currentPage;

    @Bind(R.id.list_view)
    XListView mListView;
    private String category;
    private List<DayData> dayDatas = new ArrayList<>();

    public BaseFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance(String category) {
        BaseFragment fragment = new BaseFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setAutoLoadEnable(true);
        mListView.setXListViewListener(this);
        mListView.autoRefresh();
        adapter = new QuickAdapter<DayData>(getActivity(), R.layout.listitem_base, dayDatas) {
            @Override
            protected void convert(BaseAdapterHelper helper, DayData item, int position) {
                String meta = "Via" + item.getWho() + " @ " + item.getPublishedAt();
                helper.setText(R.id.tv_desc, item.getDesc());
                helper.setText(R.id.tv_meta, meta);
            }
        };
        mListView.setAdapter(adapter);

    }


    @Override
    public void onRefresh() {
        currentPage = 1;
        Api.getData(category, currentPage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
                        if (!baseResponse.isError()) {
                            adapter.replaceAll(baseResponse.getResults());
                        }

                        mListView.stopRefresh();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DebugLog.e(error.toString());
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        mListView.stopRefresh();
                    }
                });

    }

    @Override
    public void onLoadMore() {
        Api.getData(category, ++currentPage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
                        if (!baseResponse.isError()) {
                            adapter.addAll(baseResponse.getResults());
                        }

                        mListView.stopLoadMore();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        mListView.stopLoadMore();

                    }
                });
    }
}

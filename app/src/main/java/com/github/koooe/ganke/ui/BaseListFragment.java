package com.github.koooe.ganke.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import ganke.adapter.BaseAdapterHelper;
import ganke.adapter.QuickAdapter;

/**
 *
 * 几个通用的页
 *
 */
public class BaseListFragment extends BaseFragment implements XListView.IXListViewListener {

    public QuickAdapter<DayData> adapter;
    int currentPage;

    @Bind(R.id.list_view)
    XListView mListView;

    public BaseListFragment() {
        // Required empty public constructor
    }

    public static BaseFragment newInstance(String category) {
        BaseListFragment fragment = new BaseListFragment();
        Bundle args = new Bundle();
        args.putString(BaseListFragment.ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DebugLog.e("position:" + position);
                DebugLog.e("id:" + id);
                String urls = adapter.getItem(position - 1).getUrl();
                Intent intent = new Intent(getActivity().getApplicationContext(), WebActivity.class);
                intent.putExtra(WebActivity.EXTRA_URL, urls);
                startActivity(intent);
            }
        });
        mListView.setAdapter(adapter);
    }


    @Override
    public void onRefresh() {
        final int requestPage = 1;
        Api.getData(category, requestPage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
                        if (!baseResponse.isError()) {
                            if (baseResponse.getResults().size() > 0) {
                                adapter.replaceAll(baseResponse.getResults());
                                currentPage = requestPage;
                                // 可能是disable状态
                                mListView.setPullLoadEnable(true);
                            }
                        }

                        onFinish();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DebugLog.e(error.toString());
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        onFinish();
                    }
                });

    }

    @Override
    public void onLoadMore() {
        final int requestPage = currentPage + 1;
        Api.getData(category, requestPage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
                        if (!baseResponse.isError()) {
                            if (baseResponse.getResults().size() > 0) {
                                adapter.addAll(baseResponse.getResults());
                                currentPage = requestPage;
                            } else {  // 没有更多
                                mListView.setPullLoadEnable(false);
                            }
                        }
                        onFinish();
                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        onFinish();
                    }
                });
    }

    private void onFinish() {
        mListView.stopRefresh();
        mListView.stopLoadMore();
        mListView.setRefreshTime(getTime());
    }

    private String getTime() {
        return new SimpleDateFormat("MM-dd HH:mm", Locale.CHINA).format(new Date());
    }
}

package com.github.koooe.ganke.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.koooe.ganke.R;
import com.github.koooe.ganke.adapter.FuliAdapter;
import com.github.koooe.ganke.api.Api;
import com.github.koooe.ganke.bean.BaseResponse;
import com.github.koooe.ganke.bean.DayData;
import com.github.koooe.ganke.util.DebugLog;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 尼玛是妹子图了，不想再抄了…
 */
public class FuliFragment extends BaseFragment {

    @Bind(R.id.recycler_fuli)
    RecyclerView recyclerView;

    int currentPage = 1;

    List<DayData> datas = new ArrayList<>();
    FuliAdapter fuliAdapter;

    public static FuliFragment newInstance(String category) {
        FuliFragment fragment = new FuliFragment();
        Bundle args = new Bundle();
        args.putString(BaseListFragment.ARG_CATEGORY, category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fuli, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initRecyclerView();
        refreshData();
    }

    private void refreshData() {
        final int requestPage =1;
        Api.getData(category, requestPage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        BaseResponse baseResponse = gson.fromJson(response, BaseResponse.class);
                        if (!baseResponse.isError()) {
                            if (baseResponse.getResults().size() > 0) {
                                currentPage = requestPage;
                                datas.clear();
                                datas.addAll(baseResponse.getResults());
                                fuliAdapter.notifyDataSetChanged();
                            }
                        }


                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        DebugLog.e(error.toString());
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void initRecyclerView() {
        fuliAdapter = new FuliAdapter(getActivity(), datas);
        fuliAdapter.setOnItemClickListener(new FuliAdapter.OnItemClickListener() {
            @Override
            public void onClick(DayData data) {
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra(WebActivity.EXTRA_URL, data.getUrl());
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setAdapter(fuliAdapter);
    }
}

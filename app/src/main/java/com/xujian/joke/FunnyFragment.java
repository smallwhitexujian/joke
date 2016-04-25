package com.xujian.joke;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xj.utils.View.RefreshLayout.SwipyRefreshLayout;
import com.xj.utils.View.RefreshLayout.SwipyRefreshLayoutDirection;
import com.xj.utils.utils.DebugLogs;
import com.xujian.joke.Adapter.RecyclerViewAdapter;
import com.xujian.joke.Adapter.funRecyclerViewAdapter;
import com.xujian.joke.Model.FunnyPic;
import com.xujian.joke.Utils.SharePreferenceUtils;

import java.util.ArrayList;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/17
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/17          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class FunnyFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ArrayList<FunnyPic.contentData> listData = new ArrayList<>();
    private funRecyclerViewAdapter adapter;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    protected DemoApi demoApi;
    private int pageNum = 0;
    private boolean ispull = false;

    public FunnyFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FunnyFragment newInstance() {
        FunnyFragment fragment = new FunnyFragment();
        return fragment;
    }

    @Override
    protected void doFragmentHandler(Message msg) {
        super.doFragmentHandler(msg);
        switch (msg.what) {
            case DemoApi.FUNNYPIC:
                if (!ispull) {
                    listData = (ArrayList<FunnyPic.contentData>) msg.obj;
                } else {
                    listData.addAll((ArrayList<FunnyPic.contentData>) msg.obj);
                }
                DebugLogs.d("----->" + listData.size());
                adapter.addData(listData);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initView(rootView);
        initData();
        return rootView;
    }

    private void initData() {
        demoApi = new DemoApi(getActivity(), fragmentHandler);
        getFunnyData();
    }

    private void initView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new funRecyclerViewAdapter(getActivity(), listData);
        mRecyclerView.setAdapter(adapter);
        mSwipyRefreshLayout = (SwipyRefreshLayout) rootView.findViewById(R.id.pullToRefreshView);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
    }

    /**
     * 获取JOKE数据
     */
    private void getFunnyData() {
        ispull = false;
        demoApi.getFunnyPic(0);
    }

    @Override
    public void onRefresh(final SwipyRefreshLayoutDirection direction) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    mSwipyRefreshLayout.setRefreshing(false);
                    getFunnyData();
                } else {
                    ispull = true;
                    pageNum++;
                    demoApi.getFunnyPic(pageNum);
                    mSwipyRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}

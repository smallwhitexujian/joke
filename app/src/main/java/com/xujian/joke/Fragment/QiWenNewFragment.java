package com.xujian.joke.Fragment;

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
import com.xujian.joke.Adapter.QiWenNewAdapter;
import com.xujian.joke.DemoApi;
import com.xujian.joke.Model.QiWenNew;
import com.xujian.joke.R;
import com.xujian.joke.Utils.SharePreferenceUtils;

import java.util.ArrayList;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/29
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/29          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class QiWenNewFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ArrayList<QiWenNew> listData = new ArrayList<>();
    private QiWenNewAdapter adapter;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    protected DemoApi demoApi;
    private int pageNum = 0;
    private boolean ispull = false;
    private SharePreferenceUtils sp;

    public QiWenNewFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static QiWenNewFragment newInstance() {
        QiWenNewFragment fragment = new QiWenNewFragment();
        return fragment;
    }

    @Override
    protected void doFragmentHandler(Message msg) {
        super.doFragmentHandler(msg);
        switch (msg.what) {
            case DemoApi.QIWENNEW://奇闻新闻
                if (!ispull) {
                    listData = (ArrayList<QiWenNew>) msg.obj;
                } else {
                    listData.addAll((ArrayList<QiWenNew>) msg.obj);
                }
                DebugLogs.d("----->" + listData.size());
                sp.putJokecount(pageNum);
                adapter.addData(listData);
                mSwipyRefreshLayout.setRefreshing(false);
                break;
            case DemoApi.FUNNYPIC:
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
        sp = SharePreferenceUtils.getmInstance(getActivity());
        pageNum = sp.getJokeCount();
        getData();
    }

    private void initView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new QiWenNewAdapter(getActivity(), listData);
        mRecyclerView.setAdapter(adapter);
        mSwipyRefreshLayout = (SwipyRefreshLayout) rootView.findViewById(R.id.pullToRefreshView);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
    }

    /**
     * 获取JOKE数据
     */
    private void getData() {
        ispull = false;
        demoApi.getQiWenNew(0);
    }

    @Override
    public void onRefresh(final SwipyRefreshLayoutDirection direction) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    getData();
                } else {
                    ispull = true;
                    pageNum++;
                    demoApi.getQiWenNew(pageNum);
                }
            }
        });
    }
}

package com.xujian.joke.Fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xj.utils.View.RefreshLayout.SwipyRefreshLayout;
import com.xj.utils.View.RefreshLayout.SwipyRefreshLayoutDirection;
import com.xujian.joke.Adapter.QiWenNewAdapter;
import com.xujian.joke.DemoApi;
import com.xujian.joke.Model.WEIXINGJX;
import com.xujian.joke.R;

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
public class WeiXinJinXFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ArrayList<WEIXINGJX> listData = new ArrayList<>();
    private QiWenNewAdapter adapter;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    protected DemoApi demoApi;
    private int pageNum = 1;
    private boolean ispull = false;
    private String keyword = "中国";

    public WeiXinJinXFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static WeiXinJinXFragment newInstance() {
        WeiXinJinXFragment fragment = new WeiXinJinXFragment();
        return fragment;
    }

    @Override
    protected void doFragmentHandler(Message msg) {
        super.doFragmentHandler(msg);
        switch (msg.what) {
            case DemoApi.WEIXINGJX://奇闻新闻
                if (!ispull) {
                    listData = (ArrayList<WEIXINGJX>) msg.obj;
                } else {
                    listData.addAll((ArrayList<WEIXINGJX>) msg.obj);
                }
                adapter.addData(listData);
                mSwipyRefreshLayout.setRefreshing(false);
                break;
            case DemoApi.NON:
                mSwipyRefreshLayout.setRefreshing(false);
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
        demoApi.getWeixing(1, keyword);
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
                    demoApi.getWeixing(pageNum, keyword);
                }
            }
        });
    }
}

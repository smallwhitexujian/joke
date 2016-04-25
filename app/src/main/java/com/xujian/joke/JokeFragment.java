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
import com.xujian.joke.Model.JokeModel;
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
public class JokeFragment extends BaseFragment implements SwipyRefreshLayout.OnRefreshListener {
    private RecyclerView mRecyclerView;
    private ArrayList<JokeModel> listData = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private SwipyRefreshLayout mSwipyRefreshLayout;
    protected DemoApi demoApi;
    private int pageNum = 0;
    private boolean ispull = false;
    private SharePreferenceUtils sp;

    public JokeFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static JokeFragment newInstance() {
        JokeFragment fragment = new JokeFragment();
        return fragment;
    }

    @Override
    protected void doFragmentHandler(Message msg) {
        super.doFragmentHandler(msg);
        switch (msg.what) {
            case DemoApi.JOKESUCCESS:
                if (!ispull) {
                    listData = (ArrayList<JokeModel>) msg.obj;
                } else {
                    listData.addAll((ArrayList<JokeModel>) msg.obj);
                }
                DebugLogs.d("----->" + listData.size());
                sp.putJokecount(pageNum);
                adapter.addData(listData);
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
        getJokeData();
    }

    private void initView(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity(), listData);
        mRecyclerView.setAdapter(adapter);
        mSwipyRefreshLayout = (SwipyRefreshLayout) rootView.findViewById(R.id.pullToRefreshView);
        mSwipyRefreshLayout.setOnRefreshListener(this);
        mSwipyRefreshLayout.setDirection(SwipyRefreshLayoutDirection.BOTH);
    }

    /**
     * 获取JOKE数据
     */
    private void getJokeData() {
        ispull = false;
        if (pageNum == 0) {
            sp.putJokecount(0);
        }
        demoApi.getJoke(sp.getJokeCount());
    }

    @Override
    public void onRefresh(final SwipyRefreshLayoutDirection direction) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (direction == SwipyRefreshLayoutDirection.TOP) {
                    mSwipyRefreshLayout.setRefreshing(false);
                    ispull = false;
                } else {
                    ispull = true;
                    pageNum++;
                    demoApi.getJoke(pageNum);
                    mSwipyRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}

package com.xujian.joke.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.xj.utils.utils.DebugLogs;
import com.xujian.joke.Adapter.SectionsPagerAdapter;
import com.xujian.joke.AppState;
import com.xujian.joke.Fragment.FunnyFragment;
import com.xujian.joke.Fragment.JokeFragment;
import com.xujian.joke.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private String[] mTitle = {"笑话","搞笑图片"};
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();
    private Fragment jokeFragment,funnyfragment;
    private ViewGroup linearLayout;
    private BannerView bannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initBanner();
    }

    private void initBanner() {
        linearLayout = (ViewGroup)findViewById(R.id.ads);
        bannerView = new BannerView(this, ADSize.BANNER, AppState.APPID,AppState.BannerPosID);
        // 广告请求数据，可以设置广告轮播时间，默认为30s
        //在banner广告上展示关闭按钮
        bannerView.setRefresh(10);
        bannerView.setShowClose(true);
        bannerView.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(int arg0) {
                DebugLogs.i("AD_DEMO"+ "BannerNoAD，eCode=" + arg0);
            }

            @Override
            public void onADReceiv() {
                DebugLogs.i("AD_DEMO"+"ONBannerReceive");
            }
        });
        linearLayout.addView(bannerView);
        bannerView.loadAD();

    }

    private void initview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("笑话");
        jokeFragment = JokeFragment.newInstance();
        funnyfragment = FunnyFragment.newInstance();
        fragmentArrayList.add(jokeFragment);
        fragmentArrayList.add(funnyfragment);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),mTitle,fragmentArrayList);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

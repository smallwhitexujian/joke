package com.xujian.joke.Activity;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;
import com.xj.utils.utils.DebugLogs;
import com.xujian.joke.Adapter.SectionsPagerAdapter;
import com.xujian.joke.AppState;
import com.xujian.joke.Fragment.FunnyFragment;
import com.xujian.joke.Fragment.JokeFragment;
import com.xujian.joke.Fragment.WeiXinJinXFragment;
import com.xujian.joke.R;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private String[] mTitle = {"笑话","搞笑图片","微信精选"};
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
        initBanner();
    }

    private void initBanner() {
        ViewGroup linearLayout = (ViewGroup)findViewById(R.id.ads);
        BannerView bannerView = new BannerView(this, ADSize.BANNER, AppState.APPID,AppState.BannerPosID);
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
        assert linearLayout != null;
        linearLayout.addView(bannerView);
        bannerView.loadAD();

    }

    private void initview() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("笑话");
        }
        Fragment jokeFragment = JokeFragment.newInstance();
        Fragment funnyfragment = FunnyFragment.newInstance();
//        Fragment weiXinJinXfragment = WeiXinJinXFragment.newInstance();
        fragmentArrayList.add(jokeFragment);
        fragmentArrayList.add(funnyfragment);
//        fragmentArrayList.add(weiXinJinXfragment);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),mTitle,fragmentArrayList);
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        assert mViewPager != null;
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(mViewPager);
    }

}

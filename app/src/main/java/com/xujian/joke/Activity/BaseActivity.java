package com.xujian.joke.Activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.appcompat.app.AppCompatActivity;

import com.xujian.joke.DemoApi;

import java.lang.ref.WeakReference;

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
public class BaseActivity extends AppCompatActivity {
    protected DemoApi demoApi;
    public UiHandler uiHandler = new UiHandler(this);

    public static class UiHandler extends Handler {
        private final WeakReference<BaseActivity> activity;

        public UiHandler(BaseActivity activity) {
            this.activity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseActivity activity = this.activity.get();
            if (activity != null) {
                activity.doHandler(msg);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        demoApi = new DemoApi(this, uiHandler);
    }

    protected void doHandler(Message message) {
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //解决系统字体大小修改布局错乱的问题
        Resources resource = getResources();
        Configuration c = resource.getConfiguration();
        c.fontScale = 1.0f;
        resource.updateConfiguration(c, resource.getDisplayMetrics());
        super.onResume();
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }
}

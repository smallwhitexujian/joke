package com.xujian.joke.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.ViewGroup;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.xujian.joke.AppState;
import com.xujian.joke.R;

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
public class SplashActivity extends AppCompatActivity implements SplashADListener{
    @SuppressWarnings("unused")
    private SplashAD splashAD;
    private ViewGroup container;

    public boolean canJump = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        container = (ViewGroup) this.findViewById(R.id.splash_container);
        splashAD = new SplashAD(this, container, AppState.APPID, AppState.SplashPosID, this);

        /**
         * 开屏广告现已增加新的接口，可以由开发者在代码中设置开屏的超时时长
         * SplashAD(Activity activity, ViewGroup container, String appId, String posId, SplashADListener adListener, int fetchDelay)
         * fetchDelay参数表示开屏的超时时间，单位为ms，取值范围[3000, 5000]。设置为0时表示使用广点通的默认开屏超时配置
         *
         * splashAD = new SplashAD(this, container, Constants.APPID, Constants.SplashPosID, this, 3000);可以设置超时时长为3000ms
         */
    }
    @Override
    public void onADDismissed() {
        next();
    }

    @Override
    public void onNoAD(int i) {
        /** 如果加载广告失败，则直接跳转 */
        this.startActivity(new Intent(this, MainActivity.class));
        this.finish();
    }

    @Override
    public void onADPresent() {

    }

    @Override
    public void onADClicked() {

    }

    /**
     * 设置一个变量来控制当前开屏页面是否可以跳转，当开屏广告为普链类广告时，点击会打开一个广告落地页，此时开发者还不能打开自己的App主页。当从广告落地页返回以后，
     * 才可以跳转到开发者自己的App主页；当开屏广告是App类广告时只会下载App。
     */
    private void next() {
        if (canJump) {
            this.startActivity(new Intent(this, MainActivity.class));
            this.finish();
        } else {
            canJump = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        canJump = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (canJump) {
            next();
        }
        canJump = true;
    }

    /** 开屏页最好禁止用户对返回按钮的控制 */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}

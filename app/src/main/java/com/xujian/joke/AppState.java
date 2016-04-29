package com.xujian.joke;

import android.app.Application;

import com.qq.e.ads.cfg.MultiProcessFlag;
import com.qq.e.comm.managers.GDTADManager;
import com.xj.frescolib.Config.FrescoHelper;

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
public class AppState extends Application {
    public static final String APPID = "1105292611";
    public static final String BannerPosID = "5010717039424030";
    public static final String SplashPosID = "3070910049515949";
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化fresco配置
        FrescoHelper.frescoInit(this);
    }
}

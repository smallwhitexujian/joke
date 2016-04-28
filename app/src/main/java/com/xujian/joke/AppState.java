package com.xujian.joke;

import android.app.Application;
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
    public static final String APP_ID = "100039172";
    public static final String SECRET_KEY = "4f27fe8eebc545e877a5980e26c49cdf";
    public static final String BANNER = "719323ca8816aa2fa18d3ba7cf10c517";
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化fresco配置
        FrescoHelper.frescoInit(this);
    }
}

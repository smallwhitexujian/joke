package com.xujian.joke;

import android.app.Application;

import com.xj.frescolib.FrescoHelper;

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
public class AppState extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化fresco配置
        FrescoHelper.frescoInit(this);
    }
}

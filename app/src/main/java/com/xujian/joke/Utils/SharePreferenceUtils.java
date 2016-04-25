package com.xujian.joke.Utils;

import android.content.Context;

import com.xj.utils.utils.SharedPreferencesUtil;

/**
 * Created by:      xujian
 * Version          ${version}
 * Date:            16/4/18
 * Description(描述):
 * Modification  History(历史修改):
 * Date              Author          Version
 * ---------------------------------------------------------
 * 16/4/18          xujian         ${version}
 * Why & What is modified(修改原因):
 */
public class SharePreferenceUtils {
    private static SharePreferenceUtils mInstance;
    private SharedPreferencesUtil sp;
    private static final String JOKENUM = "jokeCount";

    public static SharePreferenceUtils getmInstance(Context context){
        if (mInstance == null){
            mInstance = new SharePreferenceUtils(context);
        }
        return mInstance;
    }

    public SharePreferenceUtils(Context context) {
        super();
        sp = SharedPreferencesUtil.getInstance(context);
    }

    public void putJokecount(int num){
        sp.putInt(JOKENUM,num);
    }

    public int getJokeCount(){
        return sp.getInt(JOKENUM,0);
    }
}

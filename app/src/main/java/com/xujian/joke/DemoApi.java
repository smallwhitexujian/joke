package com.xujian.joke;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.DebugUtils;

import com.google.gson.reflect.TypeToken;
import com.xj.utils.Http.HttpManager;
import com.xj.utils.utils.DebugLogs;
import com.xj.utils.utils.JsonUtil;
import com.xujian.joke.Model.CommonListModel;
import com.xujian.joke.Model.CommonModel;
import com.xujian.joke.Model.FunnyPic;
import com.xujian.joke.Model.JokeModel;
import com.xujian.joke.Model.QiWenNew;

import java.util.List;

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
public class DemoApi {
    private static final String key = "e74a8ac6e637ba28cd9bab4ffdbdb1cb";//聚合的key
    private static final String AF_key = "2aef0c4557254305ba4824857c533839";//聚合的key
    private static final String JH_FUNNY = "http://japi.juhe.cn/joke/content/list.from";//聚合笑话地址
    private static final String JH_FUNNYPIC = "http://japi.juhe.cn/joke/img/text.from";//聚合笑话地址
    private static final String AF_NEWQIWEN = "http://api.avatardata.cn/QiWenNews/Query";//阿凡达数据
    private static final String JOKEAPI = "http://api.1-blog.com/biz/bizserver/xiaohua/list.do?size=20";//野笑话地址

    private String JH_Funny = JH_FUNNY + "?key=" + key;
    private String JH_FunnyPic = JH_FUNNYPIC + "?key=" + key;
    private String AF_QiWenNew = AF_NEWQIWEN + "?key=" + AF_key;
    public static final int API = 0X88;
    public static final int JOKESUCCESS = API + 1;
    public static final int FUNNYPIC = API + 2;
    public static final int QIWENNEW = API + 3;

    private HttpManager.CallBack callBack;
    private Activity mContext;
    private Handler mHandler;
    private static DemoApi instance;

    public static DemoApi getInstance() {
        if (instance == null) {
            instance = new DemoApi();
        }
        return instance;
    }

    public DemoApi() {
        super();
    }

    public DemoApi(Activity activity, Handler handler) {
        this.mContext = activity;
        this.mHandler = handler;
    }

    public void getJoke(int page) {
        String joke = JOKEAPI + "&page=" + page;
        callBack = new HttpManager.CallBack() {
            @Override
            public void onSuccess(String result) {
                if (!result.isEmpty()) {
                    CommonListModel<JokeModel> models = JsonUtil.fromJson(result, new TypeToken<CommonListModel<JokeModel>>() {
                    }.getType());
                    if (models != null && models.status.equals("000000")) {
                        Message message = mHandler.obtainMessage();
                        message.what = JOKESUCCESS;
                        message.obj = models.detail;
                        mHandler.sendMessage(message);
                    }
                }
            }
        };
        HttpManager.Request(mContext, HttpManager.Method.GET, joke, callBack, null);
    }

    public void getFunny(int page) {
        String FunnyPic = JH_Funny + "&page=" + page + "&pagesize=20&sort=desc&time=" + System.currentTimeMillis() / 1000;
        callBack = new HttpManager.CallBack() {
            @Override
            public void onSuccess(String result) {
                if (!result.isEmpty()) {
                    CommonListModel<JokeModel> models = JsonUtil.fromJson(result, new TypeToken<CommonListModel<JokeModel>>() {
                    }.getType());
                }
            }
        };
        HttpManager.Request(mContext, HttpManager.Method.GET, FunnyPic, callBack, null);
    }

    /**
     * 获取最新的搞笑图片
     * @param page 页数
     */
    public void getFunnyPic(int page) {
        String FunnyPic = JH_FunnyPic + "&page=" + page + "&pagesize=20";
        callBack = new HttpManager.CallBack() {
            @Override
            public void onSuccess(String result) {
                if (!result.isEmpty()) {
                    FunnyPic commonModel = JsonUtil.fromJson(result,FunnyPic.class);
                    if (commonModel != null && commonModel.error_code.equals("0")) {
                        Message message = mHandler.obtainMessage();
                        message.what = FUNNYPIC;
                        message.obj = commonModel.result.data;
                        mHandler.sendMessage(message);
                    }
                }
            }
        };
        HttpManager.Request(mContext, HttpManager.Method.GET, FunnyPic, callBack, null);
    }
    /**
     * 奇闻新闻
     * @param page 页数
     */
    public void getQiWenNew(int page) {
        String qiwen = AF_QiWenNew + "&page=" + page + "&rows=20";
        callBack = new HttpManager.CallBack() {
            @Override
            public void onSuccess(String result) {
                if (!result.isEmpty()) {
                    CommonListModel<QiWenNew> commonModel = JsonUtil.fromJson(result, new TypeToken<CommonListModel<QiWenNew>>() {
                    }.getType());
                    if (commonModel != null && commonModel.error_code.equals("0")) {
                        Message message = mHandler.obtainMessage();
                        message.what = QIWENNEW;
                        message.obj = commonModel.result;
                        mHandler.sendMessage(message);
                    }
                }
            }
        };
        HttpManager.Request(mContext, HttpManager.Method.GET, qiwen, callBack, null);
    }
}

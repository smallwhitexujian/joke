package com.xujian.joke;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.util.DebugUtils;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.xj.utils.Http.HttpManager;
import com.xj.utils.utils.DebugLogs;
import com.xj.utils.utils.JsonUtil;
import com.xujian.joke.Model.CommonListModel;
import com.xujian.joke.Model.CommonModel;
import com.xujian.joke.Model.FunnyPic;
import com.xujian.joke.Model.JokeModel;
import com.xujian.joke.Model.QiWenNew;
import com.xujian.joke.Model.WEIXINGJX;

import org.json.JSONException;
import org.json.JSONObject;

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
    private static final String AF_WEIXING = "http://api.avatardata.cn/WxNews/Query";
    private static final String JOKEAPI = "http://v.juhe.cn/joke/content/list.php?pagesize=20";//野笑话地址

    private String JH_Funny = JH_FUNNY + "?key=" + key;
    private String JH_FunnyPic = JH_FUNNYPIC + "?key=" + key;
    private String AF_QiWenNew = AF_NEWQIWEN + "?key=" + AF_key;
    private String AF_WeiXing = AF_WEIXING + "?key=9e56a82538414333a051f857237430f2";
    public static final int API = 0X88;
    public static final int NON = API;
    public static final int JOKESUCCESS = API + 1;
    public static final int FUNNYPIC = API + 2;
    public static final int QIWENNEW = API + 3;
    public static final int WEIXINGJX = API + 4;

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
        String joke = JOKEAPI + "&page=" + page + "&sort=asc"
                + "&key=" + key + "&time=1418816972";
        callBack = new HttpManager.CallBack() {
            @Override
            public void onSuccess(String result) {
                Log.e("TAG,", result.toString());
                if (!result.isEmpty()) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        if (jsonObject != null && jsonObject.optString("reason").equals("Success")) {
                            CommonListModel<JokeModel> models = JsonUtil.fromJson(jsonObject.optString("result"), new TypeToken<CommonListModel<JokeModel>>() {
                            }.getType());
                            Message message = mHandler.obtainMessage();
                            message.what = JOKESUCCESS;
                            message.obj = models.data;
                            mHandler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        };
        HttpManager.Request(mContext, HttpManager.Method.GET, joke, callBack, null);
    }

    public void getFunny(int page) {
        String FunnyPic = JH_Funny + "&page=" + page + "&pagesize=20&sort=desc&time=1418816972";
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
     *
     * @param page 页数
     */
    public void getFunnyPic(int page) {
        String FunnyPic = JH_FunnyPic + "&page=" + page + "&pagesize=20";
        callBack = new HttpManager.CallBack() {
            @Override
            public void onSuccess(String result) {
                if (!result.isEmpty()) {
                    FunnyPic commonModel = JsonUtil.fromJson(result, FunnyPic.class);
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
     *
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
                    if (commonModel != null && commonModel.error_code.equals("0") && commonModel.hasResult()) {
                        Message message = mHandler.obtainMessage();
                        message.what = QIWENNEW;
                        message.obj = commonModel.result;
                        mHandler.sendMessage(message);
                    } else {
                        Message message = mHandler.obtainMessage();
                        message.what = NON;
                        mHandler.sendMessage(message);
                    }
                }
            }
        };
        HttpManager.Request(mContext, HttpManager.Method.GET, qiwen, callBack, null);
    }

    /**
     * 微信精选
     *
     * @param page    页数
     * @param keyword 关键字
     */
    public void getWeixing(int page, String keyword) {
        String WEIXING = AF_WeiXing + "&page=" + page + "&rows=20" + "&keyword=" + keyword;
        callBack = new HttpManager.CallBack() {
            @Override
            public void onSuccess(String result) {
                if (!result.isEmpty()) {
                    CommonListModel<WEIXINGJX> commonModel = JsonUtil.fromJson(result, new TypeToken<CommonListModel<WEIXINGJX>>() {
                    }.getType());
                    if (commonModel != null && commonModel.error_code.equals("0") && commonModel.hasResult()) {
                        Message message = mHandler.obtainMessage();
                        message.what = WEIXINGJX;
                        message.obj = commonModel.result;
                        mHandler.sendMessage(message);
                    } else {
                        Message message = mHandler.obtainMessage();
                        message.what = NON;
                        mHandler.sendMessage(message);
                    }
                }
            }
        };
        HttpManager.Request(mContext, HttpManager.Method.GET, WEIXING, callBack, null);
    }
}

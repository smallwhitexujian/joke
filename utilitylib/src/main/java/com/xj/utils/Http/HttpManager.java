package com.xj.utils.Http;

import android.app.Activity;
import android.os.StrictMode;

import com.xj.utils.utils.DebugLogs;
import com.xj.utils.utils.ToastUtils;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by xujian on 16/1/20.
 * okHttp请求方式封装
 * 1,返回值 只会返回code[200..300)之间的请求
 * 2,可以支持Http body内容（发送到服务器不在from表单中，需要使用流的方式接）请求 post json数据或者get请求数据.
 * 3,可以支持get和post同步
 * 4,修改单独get请求
 * 5,修改单独post from表单请求 提交表单形式
 */
public class HttpManager {
    //严格控制http请求
    private static void init() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyDeath().build());
    }

    /**
     * post body json数据
     * 回调出结果
     *      异步操作请求模式
     * @param url      请求地址
     * @param callback 反馈结果
     * @param params   参数
     */
    public static void Request(final Activity mActivity, int method, String url, final CallBack callback, Map<String, String> params) {
        if (url == null || url.equals("")) {
            DebugLogs.e("请求地址为null/空");
            return;
        }
        DebugLogs.i("url::::"+url);
//        init();//Android 2.3提供一个称为严苛模式（StrictMode）的调试特性
        OkHttpClient client = new OkHttpClient();
        Request request = null;
        if (method == Method.GET) {
            String strUrl = restructureURL(Method.GET, url, params);
            request = new Request.Builder().url(strUrl).build();
        } else if (method == Method.POST) {
            if (params== null){
                ToastUtils.showToast(mActivity,"params is not null");
                return;
            }
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            RequestBody requestBody = builder.build();
            request = new Request.Builder().url(url).post(requestBody).build();
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
//                    callback.onFailure(call, e);
//                ToastUtils.showToast(mActivity,"error"+e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String result = response.body().string();
                    mActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(result);
                        }
                    });
                }
            }
        });
    }

    /**
     * post body json数据，get请求
     * 直接返回结果
     *     主线程消耗模式（一般情况禁止使用）
     * @param url    请求地址
     * @param params 参数
     */
    public static String Request(String url, int method, Map<String, String> params) {
        if (url == null || url.equals("")) {
            DebugLogs.e("请求地址为null/空");
            return null;
        }
        init();//Android 2.3提供一个称为严苛模式（StrictMode）的调试特性
        OkHttpClient client = new OkHttpClient();
        Request request;
        if (method == Method.GET) {
            String strUrl = restructureURL(Method.GET, url, params);
            request = new Request.Builder().url(strUrl).build();
        } else {
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                builder.add(entry.getKey(), entry.getValue());
            }
            RequestBody requestBody = builder.build();
            request = new Request.Builder().url(url).post(requestBody).build();
        }
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 连接拼接加工 拼接成get形式
     *
     * @param method 模型判断
     * @param url    连接判断
     * @param params 请求参数对
     * @return 返回拼接好的连接
     */
    protected static String restructureURL(int method, String url, Map<String, String> params) {
        if (method == Method.GET && params != null) {
            url = url + "?" + encodeParameters(params);
        }
        return url;
    }

    /**
     * 参数的键和值进行组装
     *
     * @param params 参数
     * @return 结果
     */
    private static String encodeParameters(Map<String, String> params) {
        if (params == null) {
            return "";
        }
        StringBuilder encodedParams = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            encodedParams.append(entry.getKey());
            encodedParams.append('=');
            encodedParams.append(entry.getValue());
            encodedParams.append('&');
        }
        String result = encodedParams.toString();
        return result.substring(0, result.length() - 1);
    }


    //模型设置
    public interface Method {
        int GET = 0;
        int POST = 1;
    }

    //结果反馈
    public interface CallBack {
        //失败处理
//        void onFailure(Call call, IOException e);

        //成功处理
        void onSuccess(String result);
    }

}

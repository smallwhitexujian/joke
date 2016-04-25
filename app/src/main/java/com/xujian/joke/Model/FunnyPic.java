package com.xujian.joke.Model;

import java.util.List;

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
public class FunnyPic {
    public String error_code;//聚合数据成功状态码
    public String reason;//聚合数据成功状态码
    public Result result;//返回数据

    @Override
    public String toString() {
        return "FunnyPic{" +
                "error_code='" + error_code + '\'' +
                ", reason='" + reason + '\'' +
                ", result=" + result +
                '}';
    }

    public static class Result {
        public List<contentData> data;

        @Override
        public String toString() {
            return "Result{" +
                    "data=" + data +
                    '}';
        }
    }

    public static class contentData {
        public String content;//内容
        public String url;//图片地址
        public String unixtime;//时间戳
        public String updatetime;//更新时间

        @Override
        public String toString() {
            return "Data{" +
                    "content='" + content + '\'' +
                    ", url='" + url + '\'' +
                    ", unixtime='" + unixtime + '\'' +
                    ", updatetime='" + updatetime + '\'' +
                    '}';
        }
    }

}




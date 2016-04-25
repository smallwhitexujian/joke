package com.xujian.joke.Model;

import java.io.Serializable;

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
public class JokeModel implements Serializable {
    public String id;//编号
    public String xhid;//编号
    public String author;//作者
    public String content;//内容
    public String picUrl;//笑话插图
    public String status;//状态

    @Override
    public String toString() {
        return "JokeModel{" +
                "id='" + id + '\'' +
                ", xhid='" + xhid + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

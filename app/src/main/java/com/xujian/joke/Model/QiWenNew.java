package com.xujian.joke.Model;

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
public class QiWenNew {
    public String ctime;//创建时间
    public String title;//标题
    public String description;//简介
    public String picUrl;//图片
    public String url;//详情

    @Override
    public String toString() {
        return "QiWenNew{" +
                "ctime='" + ctime + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

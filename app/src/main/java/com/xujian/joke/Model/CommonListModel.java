package com.xujian.joke.Model;

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
public class CommonListModel<T> extends CommonModel {
    public List<T> detail;
    public List<T> data;
    public List<T> result;

    public boolean hasDetail() {
        return detail != null && detail.size() > 0;
    }

    public boolean hasData() {
        return data != null && data.size() > 0;
    }

    public boolean hasResult() {
        return result != null && result.size() > 0;
    }
}

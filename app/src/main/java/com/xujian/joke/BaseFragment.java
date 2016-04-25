package com.xujian.joke;

import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

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
public class BaseFragment extends Fragment {

    public FragmentHandler fragmentHandler = new FragmentHandler(this);

    public static class FragmentHandler extends Handler {
        private final WeakReference<BaseFragment> weakFragment;

        public FragmentHandler(BaseFragment fragment) {
            this.weakFragment = new WeakReference<>(fragment);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BaseFragment fragment = this.weakFragment.get();
            if (fragment != null) {
                fragment.doFragmentHandler(msg);
            }
        }
    }

    protected void doFragmentHandler(Message msg) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

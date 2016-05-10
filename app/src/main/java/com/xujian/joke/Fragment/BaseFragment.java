package com.xujian.joke.Fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;

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
    private ClickPermission clickPermission;
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
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

    public void requestPermission(Context context,ClickPermission clickPermission){
        this.clickPermission = clickPermission;
        //判断当前Activity是否已经获得了该权限
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //如果App的权限申请曾经被用户拒绝过，就需要在这里跟用户做出解释
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(context,"please give me the permission",Toast.LENGTH_SHORT).show();
            } else {
                //进行权限请求
                ActivityCompat.requestPermissions((Activity) context,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case REQUEST_CODE_ASK_PERMISSIONS:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){//执行权限
                    clickPermission.hasPermission();
                }else{//没有权限
                    clickPermission.hasNoPermission();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public interface ClickPermission{
        void hasPermission();
        void hasNoPermission();
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(getActivity());
        super.onResume();
    }
}

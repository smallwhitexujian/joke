package com.xj.utils.mediaPlayer.AudioUtils;

import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.widget.Toast;

import com.xj.utils.utils.DebugLogs;

import java.io.File;

/**
 * Created by xujian on 16/2/22.
 * 扫描本地文件，找到mp3文件
 */
public class SearchMp3Utils {
    protected static final int SEARCH_MUSIC_SUCCESS = 0;// 搜索成功标记

    public SearchMp3Utils(Context context, final Handler handler) {
        //检测外部是否有存储设备
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            new Thread(new Runnable() {
                String[] ext = {".mp3"};
                File file = Environment.getExternalStorageDirectory();

                @Override
                public void run() {
                    search(file, ext);//另一种扫描歌曲方式文件扫描
                    handler.sendEmptyMessage(SEARCH_MUSIC_SUCCESS);
                }
            }).start();
        } else {
            Toast.makeText(context, "请插入外部存储设备..", Toast.LENGTH_LONG).show();
        }
    }

    //搜索音乐文件
    private void search(File file, String[] ext) {
        if (file != null) {
            if (file.isDirectory()) {
                File[] listFile = file.listFiles();
                if (listFile != null) {
                    for (int i = 0; i < listFile.length; i++) {
                        search(listFile[i], ext);
                    }
                }
            } else {
                String filename = file.getAbsolutePath();
                for (int i = 0; i < ext.length; i++) {
                    if (filename.endsWith(ext[i])) {
                        DebugLogs.d("----文件名称" + filename);
                        break;
                    }
                }
            }
        }
    }
}

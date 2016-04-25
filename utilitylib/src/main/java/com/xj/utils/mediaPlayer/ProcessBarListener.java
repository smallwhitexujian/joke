package com.xj.utils.mediaPlayer;

import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.SeekBar;

import com.xj.utils.utils.DebugLogs;


/**
 * Created by xujian on 16/2/20.
 *
 */
public class ProcessBarListener implements SeekBar.OnSeekBarChangeListener {
    private MediaPlayer mediaPlayer;

    protected ProcessBarListener(MediaPlayer Mp){
        this.mediaPlayer = Mp;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser){
            mediaPlayer.seekTo(progress);
            ShowTime(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    //时间显示函数，我们获得音乐信息的是以毫秒为单位的，把转换成我们熟悉的00：00格式
    private String ShowTime(int time){
        time/=1000;
        int minute = time / 60;
        int hour = time / 60;
        int second = time % 60;
        minute %= 60;
        return String.format("%02d:%02d",minute,second);
    }

    Handler handler = new Handler();
    public void StartbarUpdata(){
        handler.post(r);
    }
    Runnable r = new Runnable() {
        @Override
        public void run() {
            int currentPosition = mediaPlayer.getCurrentPosition();
            DebugLogs.e("----"+ShowTime(currentPosition));
            int mMax = mediaPlayer.getDuration();
            DebugLogs.e("====>歌曲最大长度"+mMax);
            DebugLogs.e("---->播放长度"+currentPosition);
            handler.postDelayed(r,100);
        }
    };
}

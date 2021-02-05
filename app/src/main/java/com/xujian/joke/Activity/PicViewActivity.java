package com.xujian.joke.Activity;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.drawable.ScalingUtils;
import com.xj.frescolib.View.FrescoDrawee;
import com.xujian.joke.R;
import com.xujian.joke.Utils.FrescoBitmapUtils;
import com.xujian.joke.Utils.ImageTouchView;

/**
 * 图片查看
 */
public class PicViewActivity extends BaseActivity {
    private ImageView picView;
    private String  picUrl;
    private ImageTouchView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picview);
        initView();
    }

    private void initView(){
        picView = (ImageView)findViewById(R.id.picture_view);
        image = (ImageTouchView) findViewById(R.id.image);
        picUrl = getIntent().getStringExtra("url");
        if (picUrl.contains(".gif")){
            picView.setVisibility(View.VISIBLE);
            image.setVisibility(View.GONE);
            Glide.with(picView.getContext()).load(picUrl).into(picView);
            picView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }else{
            image.setVisibility(View.VISIBLE);
            picView.setVisibility(View.GONE);
            FrescoBitmapUtils.getImageBitmap(PicViewActivity.this, picUrl, new FrescoBitmapUtils.BitCallBack() {
                @Override
                public void onNewResultImpl(final Bitmap bitmap) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            image.setImageBitmap(bitmap);
                        }
                    });
                }
            });

        }

    }


}

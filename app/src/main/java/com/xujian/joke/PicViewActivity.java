package com.xujian.joke;

import android.os.Bundle;
import android.view.View;

import com.facebook.drawee.drawable.ScalingUtils;
import com.xj.frescolib.View.FrescoDrawee;

/**
 * 图片查看
 */
public class PicViewActivity extends BaseActivity {
    private FrescoDrawee picView;
    private String  picUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picview);
        initView();
    }

    private void initView(){
        picView = (FrescoDrawee)findViewById(R.id.picture_view);
        picUrl = getIntent().getStringExtra("url");
        picView.setImageURI(picUrl);
        picView.setImageImageScaleType(ScalingUtils.ScaleType.FIT_CENTER);
        picView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


}

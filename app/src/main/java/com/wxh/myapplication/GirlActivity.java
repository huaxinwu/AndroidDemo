package com.wxh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 *  帧布局，点击屏幕，妹子跟着移动
 */
public class GirlActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girl);

        // 通过id获取组件
        FrameLayout frame = (FrameLayout) findViewById(R.id.fl_1);
        final GirlView girlView = new GirlView(GirlActivity.this);

        // 为我们的萌妹子添加触摸事件监听器
        girlView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 设置妹子显示的位置
                girlView.bitmapX = event.getX() - 150;
                girlView.bitmapY = event.getY() - 150;
                // 调用重绘方法
                girlView.invalidate();
                return true;
            }
        });

        // 将自定义视图添加到帧布局容器中
        frame.addView(girlView);
    }
}

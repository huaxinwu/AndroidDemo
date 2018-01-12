package com.wxh.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 *  自定义Activity来显示当前Acivity
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_base);
        // 添加一个活动
        ActivityCollector.addActivity(this);
        Log.d("BaseActivity",getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 移除一个活动
        ActivityCollector.removeActivity(this);
    }
}

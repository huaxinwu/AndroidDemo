package com.wxh.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 *  自定义广播接收器
 */
public class MyReceiver extends BroadcastReceiver {

    /**
     *  接收消息
     * @param context
     * @param intent
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"网络状态发生改变",Toast.LENGTH_SHORT).show();
    }
}

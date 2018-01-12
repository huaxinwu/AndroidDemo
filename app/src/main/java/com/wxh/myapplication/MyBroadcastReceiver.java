package com.wxh.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 *  自定义标准广播
 */
public class MyBroadcastReceiver extends BroadcastReceiver {

    private final String ACTION_BOOT = "com.wxh.broadcast.MYBROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {
        if(ACTION_BOOT.equals(intent.getAction())){
            // 收到消息，并响应给客户端
            Toast.makeText(context,"收到标准广播",Toast.LENGTH_SHORT).show();
        }

    }
}

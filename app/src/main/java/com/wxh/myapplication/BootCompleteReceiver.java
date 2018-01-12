package com.wxh.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 *  自定义开机就接收广播的广播接收器
 */
public class BootCompleteReceiver extends BroadcastReceiver {

    /**
     *  提示标识符
     */
    private final String ACTION_BOOT = "android.intent.action.BOOT_COMPLETED";

    @Override
    public void onReceive(Context context, Intent intent) {
        // 如果匹配，执行相关操作
        if(ACTION_BOOT.equals(intent.getAction())){
            Toast.makeText(context,"开机完毕",Toast.LENGTH_LONG).show();
        }

    }
}

package com.wxh.myapplication;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.view.WindowManager;

/**
 *  自定义登录广播接收器
 */
public class MyLoginReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        // 弹出对话框j建设者
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        // 设置各种参数
        dialogBuilder.setTitle("警告：");
        dialogBuilder.setMessage("您的账号在别处登录，请重新登陆~");
        // 不可撤销
        dialogBuilder.setCancelable(false);
        // 确定按钮位置添加点击事件
        dialogBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 关闭所有活动
                ActivityCollector.finishAll();
                // 从新打开一个登录活动,用一个栈来存放
                Intent intent = new Intent(context,LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

        // 获取一个弹出对话框
        AlertDialog alertDialog = dialogBuilder.create();
        // 获取一个Windows窗口并设置类型为弹出框
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        // 显示出来
        alertDialog.show();
    }
}

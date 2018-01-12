package com.wxh.myapplication;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.util.Log;

/**
 *  自定义Service
 */
public class MyService extends Service {

    private static final String TAG = "MyService";

    /**
     *  必须实现父类构造器
     */
    public MyService() {
    }

    /**
     *  绑定服务
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind方法被调用");
        return null;
    }

    /**
     *  Service被创建时调用
     */
    @Override
    public void onCreate() {
        Log.i(TAG, "onCreate方法被调用");
        super.onCreate();

    }

    /**
     *  Service被启动时调用  
     * @param intent
     * @param flags
     * @param startId
     * @return int
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand方法被调用");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     *  Service被关闭之前回调
     */
    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy方法被调用");
        super.onDestroy();
    }
}

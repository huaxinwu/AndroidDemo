package com.wxh.myapplication;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * 意图服务
 * 1.Service不是一个单独的进程,它和它的应用程序在同一个进程中
 * 2.Service不是一个线程,这样就意味着我们应该避免在Service中进行耗时操作
 */
public class MyIntentService extends IntentService {

    private static final String TAG = "hehe";

    public MyIntentService(){
        super("MyIntentService");
    }

    /**
     *  必须重写的方法
     *  处理工作线程耗时问题
     * @param intent
     */
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        // Intent是从Activity发过来的，携带识别参数，根据参数不同执行不同的任务
        String action = intent.getExtras().getString("param");
        if ("s1".equals(action)){
            Log.i(TAG, "启动service1");
        }else if("s2".equals(action)){
            Log.i(TAG, "启动service2");
        }else if("s3".equals(action)){
            Log.i(TAG, "启动service2");
        }else{
            Log.i(TAG, "没有启动任何service ");
        }
        // 让服务休眠2秒
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // 重写其他方法，查看调用顺序
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return super.onBind(intent);
    }

    @Override
    public void onCreate() {
        Log.i(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void setIntentRedelivery(boolean enabled) {
        super.setIntentRedelivery(enabled);
        Log.i(TAG,"setIntentRedelivery");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG,"onDestroy");
        super.onDestroy();
    }
}

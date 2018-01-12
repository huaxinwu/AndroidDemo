package com.wxh.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.provider.Settings;

import java.util.LinkedList;

/**
 *  自定义活动管理器类：添加、移除、销毁
 * Created by wxh on 2018/1/3.
 */

public class ActivityCollector {
    /** 列表来存储Activity  */
    private static  LinkedList<Activity> activities = new LinkedList<Activity>();

    private ActivityCollector(){

    }

    /**
     * 添加一个活动
     * @param activity
     */
    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    /**
     *  移除一个活动
     * @param activity
     */
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     *  关闭所有活动
     */
    public static void finishAll() {
        for(Activity activity:activities) {
            if(!activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     *  完全退出APP
     *  关闭所有Activity的，但是有些时候我们可能想杀死整个App，连后台任务都杀死 杀得一干二净的话
     *  任何status是非零参数，那么表示是非正常退出
     *  System.exit(0)是将你的整个虚拟机里的内容都停掉了
     *  system.exit(1):是非正常退出，就是说无论程序正在执行与否，都退出
     * @param context
     */
    public static void AppExit(Context context){
        try {
            ActivityCollector.finishAll();
            ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            // 杀掉后台进程
            activityManager.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

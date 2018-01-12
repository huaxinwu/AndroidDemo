package com.wxh.myapplication;

/**
 *  创建线程二
 * Created by wxh on 2018/1/3.
 */

public class MyRunnable implements Runnable {
    @Override
    public void run() {
        // 编写你的业务逻辑
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        MyRunnable myRunnable = new MyRunnable();
        // 1.因为Runnable接口没有start方法，作为参数在Thread构造方法中使用
        new  Thread(myRunnable).start();
        // 2.
        new Thread(new MyRunnable()).start();
    }
}

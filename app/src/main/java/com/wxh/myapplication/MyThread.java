package com.wxh.myapplication;

/**
 *  创建线程一
 * Created by wxh on 2018/1/3.
 */

public class MyThread extends Thread {
    @Override
    public void run() {
        super.run();
        // 编写你的业务逻辑
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // 1.启动线程
        new MyThread().start();
        // 2.使用匿名内部类启动线程
        // 线程数量少时候使用，慎用会发生内存泄露
        new Thread(){
            @Override
            public void run() {
                super.run();
                // 编写你的业务逻辑
            }
        }.start();
    }
}

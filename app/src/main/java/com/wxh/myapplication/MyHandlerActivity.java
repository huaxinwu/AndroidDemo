package com.wxh.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 *  Handler写在子线程中流程：
 *  1. 直接调用Looper.prepare()方法即可为当前线程创建Looper对象,而它的构造器会创建配套的MessageQueue;
 *  2. 创建Handler对象,重写handleMessage( )方法就可以处理来自于其他线程的信息了!
 *  3. 调用Looper.loop()方法启动Looper
 * @auther wxh
 * @date 2018/1/11 11:37
 */
public class MyHandlerActivity extends AppCompatActivity {

    /**
     *  上限数字
     */
    private static final String UPPER_NUM = "upper";
    private EditText etNumber;
    /**
     *  计算数字上限线程
     */
    private CalThread calThrea;

    /**
     *  构建线程
     */
    private class CalThread extends  Thread{

        /**
         *  UI控件之间消息传递
         */
        private Handler handler;

        @Override
        public void run() {
            super.run();
            // 1. 为运行消息循环准备
            Looper.prepare();
            //  2. 消息传递,重写handleMessage
            handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what == 0x123){
                        int upperNum = msg.getData().getInt(UPPER_NUM);
                        List<Integer> nums = new ArrayList<>();
                        // 计算从2开始、到upper的所有质数
                        outer:
                        for(int i=2;i<=upperNum;i++){
                            // 用i处于从2开始、到i的平方根的所有数
                            for (int j=2;j<=Math.sqrt(i);j++){
                                // 如果可以整除，表明这个数不是质数
                                if(i!= 2 && i%j == 0){
                                    continue outer;
                                }
                            }
                            // 找到质数添加到集合里
                            nums.add(i);
                        }
                        // 使用Toast显示统计出来的所有质数
                        Toast.makeText(MyHandlerActivity.this,nums.toString(),Toast.LENGTH_SHORT).show();

                    }
                }
            };
            // 启动Looper
            Looper.loop();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_handler);

        etNumber = (EditText) findViewById(R.id.et_num);
        calThrea = new CalThread();
        // 启动线程
        calThrea.start();
    }

    /**
     *  Button已经绑定点击事件，实现点击事件的方法
     * @param v
     */
    public void cal(View v){
        // 创建消息对象
        Message msg = new Message();
        msg.what = 0x123;
        // 绑定数据,传递数据
        Bundle bundle = new Bundle();
        bundle.putInt(UPPER_NUM,Integer.parseInt(etNumber.getText().toString()));
        // 设置数据
        msg.setData(bundle);
        // 向新线程中的Handler发送消息
        calThrea.handler.sendMessage(msg);
    }


}

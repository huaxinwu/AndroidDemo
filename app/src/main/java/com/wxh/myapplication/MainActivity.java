package com.wxh.myapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *  手机首次启动主界面
 */
public class MainActivity extends AppCompatActivity {

    /**
     * 帧布局按钮
     */
    private Button btnFrame;
    /**
     * 启动服务
     */
    private Button btnStart;
    /**
     *  停止服务
     */
    private Button btnStop;
    /**
     *  动态注册广播接收器
     */
    private MyReceiver myReceiver;

    /**
     *  发送标准广播消息
     */
    private Button btnSend;

    /**
     *  本地广播相关参数
     */
    private MyLoginReceiver localReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private IntentFilter intentFilter;
    private Button btnLocal;

    /**
     *  内容提供者的插入数据
     */
    private Button btnInsert;

    /**
     *  文件的读写
     */
    private EditText etFileName;
    private EditText etFileContent;
    private Button btnSave;
    private Button btnClean;
    private Button btnRead;
    private Context context;

    /**
     *  SharedPreferences保存读取数据量小的数据比如用户名、密码等等
     */
    private Button btnSp;

    /**
     *  Android API操作SQLite
     */
    private Button btnSQLite;

    /**
     *  HttpURLConnection Get请求处理
     */
    private Button btnHttpGet;

    /**
     *  Handler写在子线程中
     */
    private Button btnHandler;
    /**
     *  基于TCP通信的Socket
     */
    private Button btnSocketToTcp;



    /**
     *  创建Activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 回调方法
        super.onCreate(savedInstanceState);
        // 设置要显示的视图
        setContentView(R.layout.activity_main);

        // ****************Activity之间跳转***************************
        // 获取组件
        btnFrame = (Button) findViewById(R.id.btn_frame);
        // 设置组件点击事件
        btnFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Activity之间通过Intent跳转界面并传递数据
                // 跳转到GirlActivity显示界面
                Intent intent = new Intent(MainActivity.this,GirlActivity.class);
                startActivity(intent);
            }
        });

        // ****************Service***************************
        btnStart = (Button) findViewById(R.id.btn_start);
        btnStop = (Button) findViewById(R.id.btn_stop);
        // 创建启动Service的Intent,以及Intent属性，version5.0 隐式启动转变为显式启动
        final Intent intent = new Intent();
        // 设置执行操作
        intent.setAction("com.wxh.myapplication.MYSERVICE");
        /**
         * 需要注意的是在5.0上采用隐式启动时，会出现java.lang.IllegalArgumentException: Service Intent must be explicit异常。
         * 也就是说Service的Intent必须明确。解决方法就是给Intent设置一下具体的包名，指明具体是哪个包启动的Service。
         */
        intent.setPackage(this.getPackageName());
        // 为两个按钮设置点击事件,分别是启动与停止service
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 启动服务
                startService(intent);
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 停止服务
                stopService(intent);
            }
        });

        // ****************动态注册广播接收器,只有程序启动才能收到广播***************************
        myReceiver = new MyReceiver();
        // 意图过滤器
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        // 注册广播接收器
        registerReceiver(myReceiver,filter);

        // ****************发送标准广播消息**************************
        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发送标准广播消息
                sendBroadcast(new Intent("com.wxh.broadcast.MYBROADCAST"));
            }
        });

        // ****************本地广播消息账号下线提示**************************
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        // 初始化广播接收者，设置过滤器,动态注册广播
        localReceiver = new MyLoginReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("com.wxh.myloginreceiver.LOGIN_OTHER");
        localBroadcastManager.registerReceiver(localReceiver,intentFilter);
        // t跳转到登录界面
        btnLocal = (Button) findViewById(R.id.btn_local);
        btnLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.wxh.myloginreceiver.LOGIN_OTHER");
                // 发送本地广播消息
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        // ****************ContentProvider 插入数据**************************
        btnInsert = (Button) findViewById(R.id.btn_insert);
        // 读取ContentProvider数据
        final ContentResolver resolver = this.getContentResolver();
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 上下文值集合
                ContentValues values = new ContentValues();
                values.put("name","测试");
                // 获取添加的Uri
                Uri uri = Uri.parse("content://com.wxh.providers.myprovider/test");
                /// 插入数据
                resolver.insert(uri,values);
                Toast.makeText(getApplicationContext(), "数据插入成功", Toast.LENGTH_SHORT).show();
            }
        });

        // ****************ContentProvider 注册监听器**************************
        getContentResolver().registerContentObserver(Uri.parse("cotent://sms"),true,
                new MyContentObserver(new Handler()));


        // ****************文件的读写**************************
        context = getApplicationContext();
        // 绑定视图
        bindViews();

        // ****************SharedPreferences读写**************************
        btnSp = (Button) findViewById(R.id.btn_sp);
        btnSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到SharedPreferences读写界面
                Intent intentSp = new Intent(MainActivity.this,MySharedPreferencesActivity.class);
                startActivity(intentSp);
            }
        });

        // ****************Android API操作SQLite**************************
        btnSQLite = (Button) findViewById(R.id.btn_sqlite);
        btnSQLite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到sqliteapi界面
                Intent intentSQLite = new Intent(MainActivity.this,SQLiteAPIActivity.class);
                startActivity(intentSQLite);
            }
        });

        // ****************HttpURLConnection Get**************************
        btnHttpGet = (Button) findViewById(R.id.btn_httpget);
        btnHttpGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到HttpURLConnection Get界面
                Intent intent1HttpGet = new Intent(MainActivity.this,HttpGetActivity.class);
                startActivity(intent1HttpGet);
            }
        });

        // ****************Handler写在子线程中**************************
        btnHandler = (Button) findViewById(R.id.btn_handler);
        btnHandler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到MyHandlerActivity界面
                Intent intent1Handler = new Intent(MainActivity.this,MyHandlerActivity.class);
                startActivity(intent1Handler);
            }
        });

        // ****************基于TCP通信的Socket**************************
        btnSocketToTcp = (Button) findViewById(R.id.btn_sockettotcp);
        btnSocketToTcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 因为Android不允许在主线程(UI线程)中做网络操作，所以这里需要我们自己 另开一个线程来连接Socket！
                // 启动一个程序来接受服务端响应的数据
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        try {
                            acceptServer();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });

    }

    /**
     *  销毁Activity
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 将全局广播取消掉
        unregisterReceiver(myReceiver);
        //  将本地广播取消掉
        localBroadcastManager.unregisterReceiver(localReceiver);
    }

    /**
     *  自定义内容监听器，监听数据变化
     */
    private final class MyContentObserver extends ContentObserver{
        /**
         *  实现父类构造器
         * @param handler
         */
        public MyContentObserver(Handler handler) {
            super(handler);
        }

        /**
         *  重写改变方法
         * @param selfChange
         */
        @Override
        public void onChange(boolean selfChange) {
            // 查询发送箱里的短信(处在正在发送状态的信息放在发送箱)
            Cursor cursor = getContentResolver().query(Uri.parse("content://sms/outbox"),null,null,null,null);
            // 遍历查询获取结果集,就可以获取用户正在发送的短信
            while(cursor.moveToNext()){
                // 用于拼接字符串
                StringBuilder sb = new StringBuilder();
                // 发送地址
                sb.append("address=").append(cursor.getString(cursor.getColumnIndex("address")));
                // 短信标题
                sb.append(";subject").append(cursor.getString(cursor.getColumnIndex("subject")));
                // 短信内容
                sb.append(";body").append(cursor.getString(cursor.getColumnIndex("body")));
                // 短信发送时间
                sb.append(";time").append(cursor.getLong(cursor.getColumnIndex("date")));
                // 打印发送的短信
                System.out.println("用户发送出去的信息："+sb.toString());
             }
        }
    }

    /**
     *  获取视图组件，并添加点击事件
     */
    private void bindViews(){
        etFileName = (EditText) findViewById(R.id.et_filename);
        etFileContent = (EditText) findViewById(R.id.et_filecontent);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnClean = (Button) findViewById(R.id.btn_clean);
        btnRead = (Button) findViewById(R.id.btn_read);

        // 添加点击事件
        btnSave.setOnClickListener(new OnClick());
        btnClean.setOnClickListener(new OnClick());
        btnRead.setOnClickListener(new OnClick());
    }

    /**
     *  封装点击监听器实现
     */
    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.btn_save:
                    // 通过文件输出流，写入数据
                    FileHelper helper = new FileHelper(context);
                    // 通过组件文件内容获取输入的参数
                    String fileName = etFileName.getText().toString();
                    String fileContent = etFileContent.getText().toString();
                    try {
                        // 保存数据
                        helper.save(fileName,fileContent);
                        Toast.makeText(context,"数据写入成功",Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(context,"数据写入失败",Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.btn_clean:
                    // 情况输入框的数据
                    etFileName.setText("");
                    etFileContent.setText("");
                    break;
                case R.id.btn_read:
                    String result = "";
                    // 通过文件输入流，读取数据
                    FileHelper helper2 = new FileHelper(context);
                    try {
                        String fName = etFileName.getText().toString();
                        result = helper2.read(fName);
                        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     *  客户端接受服务端响应的信息
     * @throws IOException
     */
    private void acceptServer() throws  IOException{
        // 1.创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("192.168.1.52",12345);
        // 2.获取输出流，向服务器端发送信息
        OutputStream out = socket.getOutputStream();
        // 将字节流包装为字符流
        PrintWriter writer = new PrintWriter(out);
        // 获取客户端的IP地址
        InetAddress address = InetAddress.getLocalHost();
        String ip = address.getHostAddress();
        writer.write("客户端：~" + ip + "~ 接入服务器！！");
        writer.flush();
        // 关闭socket输出流
        socket.shutdownOutput();
        // 关闭socket
        socket.close();
    }


}

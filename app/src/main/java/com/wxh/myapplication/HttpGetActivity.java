package com.wxh.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

/**
 *  HTTP Get请求示例
 * @auther wxh
 * @date 2018/1/10 14:29
 */
public class HttpGetActivity extends AppCompatActivity {

    private TextView tvMenu;
    private ImageView iv1;
    private ScrollView sv1;
    private TextView tvShow;
    private WebView wv1;
    private Bitmap bitmap;
    private String detail = "";

    /**
     *  图片的URL
     */
    private static final String PIC_URL = "http://ww2.sinaimg.cn/large/7a8aed7bgw1evshgr5z3oj20hs0qo0vq.jpg";

    /**
     *  HTML的URL
     */
    private static final String  HTML_URL = "https://www.baidu.com";

    /**
     *  用于刷新界面
     *  最好不要这样写，匿名内部类，容易内存泄漏
     *  一般采用静态内部类和弱引用
     */
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            // 十六进制 0x开头不是字母o
            switch (msg.what){
                case 0x001:
                    // 隐藏所有控件
                    hideAllWidget();
                    // 设置图片控件可见、位图
                    iv1.setVisibility(View.VISIBLE);
                    iv1.setImageBitmap(bitmap);
                    Toast.makeText(HttpGetActivity.this,"图片加载完毕",Toast.LENGTH_SHORT).show();
                    break;
                case 0x002:
                    hideAllWidget();
                    // 滚动控件可见、文件内容
                    sv1.setVisibility(View.VISIBLE);
                    tvShow.setText(detail);
                    Toast.makeText(HttpGetActivity.this,"HTML代码加载完毕",Toast.LENGTH_SHORT).show();
                    break;
                case 0x003:
                    hideAllWidget();
                    // web控件可见、网页加载地址
                    wv1.setVisibility(View.VISIBLE);
                    // 如果网页中有JS，必须指定网页JS脚本
                    WebSettings settings = wv1.getSettings();
                    settings.setJavaScriptEnabled(true);
                    // 加载html代码的使用的是webView的loadDataWithBaseURL而非LoadData， 如果用LoadData又要去纠结中文乱码的问题
                    wv1.loadDataWithBaseURL("", detail, "text/html", "UTF-8", "");
                    Toast.makeText(HttpGetActivity.this,"网页加载完毕",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }

        }
    };

    /**
     *  隐藏所有控件
     */
    private void hideAllWidget(){
        iv1.setVisibility(View.GONE);
        sv1.setVisibility(View.GONE);
        wv1.setVisibility(View.GONE);
    }

    /**
     *  获取UI控件
     */
    private void setViews(){
        tvMenu = (TextView) findViewById(R.id.tv_menu);
        tvShow = (TextView) findViewById(R.id.tv_show);
        iv1 = (ImageView) findViewById(R.id.iv_1);
        sv1 = (ScrollView) findViewById(R.id.sv_1);
        wv1 = (WebView) findViewById(R.id.wv_1);
        // 注册上下文菜单
        registerForContextMenu(tvMenu);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_get);
        setViews();
    }

    /**
     *  重写上下文菜单创建方法
     * @param menu
     * @param v
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        // LayoutInflater是用来实例化整个布局文件，而 MenuInflater是用来实例化Menu目录下的Menu布局文件的
        MenuInflater inflater = new MenuInflater(this);
        // 编写菜单xml和上下文菜单xml,生成三个子菜单
        inflater.inflate(R.menu.menus, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    /**
     *  上下文菜单被点击选中方法
     * @param item
     * @return boolean
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.one:
                // 启动一个线程发送图片
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            // 获取图片数据
                            byte[] data = GetData.getImage(PIC_URL);
                            // 获取位图对象
                            bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        // 发送消息
                        handler.sendEmptyMessage(0x001);
                    }
                }.start();
                break;
            case R.id.two:
                // 启动一个线程发送HTML代码
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            // 获取HTML数据
                            detail = GetData.getHtml(HTML_URL);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        // 发送消息
                        handler.sendEmptyMessage(0x002);
                    }
                }.start();
                break;
            case R.id.three:
                if("".equals(detail)){
                    Toast.makeText(HttpGetActivity.this,"先请求HTML先嘛~",Toast.LENGTH_SHORT).show();
                }else{
                    handler.sendEmptyMessage(0x003);
                }
                break;
            default:
                break;
        }
        return true;
    }
}

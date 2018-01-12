package com.wxh.myapplication;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * 用户登录账号下线演示界面
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    /**
     *  SharedPreferences作为Android存储数据方式之一
     *  是以一种键值对存储数据集合
     *  特点：
     *  1.只支持Java基本数据类型，不支持自定义数据类型
     *  2.应用内数据共享
     *  3.使用简单
     */
    private SharedPreferences pref;

    /**
     *  共享数据编辑器
     */
    private SharedPreferences.Editor editor;
    //  布局控件
    private EditText etUser;
    private EditText etPwd;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // 获取共享引用实例,指定文件名和访问权限
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        // 获取布局控件，添加点击事件
        bindViews();

    }


    @Override
    protected void onStart() {
        super.onStart();
        // 清空用户名和面
        if(pref.getString("user","").equals("")){
            etUser.setText(pref.getString("user",""));
            etPwd.setText(pref.getString("pwd",""));
        }

    }

    /**
     *  重写OnClickListener接口方法
     * @param v
     */
    @Override
    public void onClick(View v) {
        // 获取用户名和密码
        String user = etUser.getText().toString();
        String pwd = etPwd.getText().toString();
        // 用户名和密码都匹配正确，进行相关操作
        if("123".equals(user) && "123".equals(pwd)){
            // 获取一个编辑器
            editor = pref.edit();
            // 将用户名和密码保存在编辑器中
            editor.putString("user",user);
            editor.putString("pwd",pwd);
            // 提交数据
            editor.commit();

            // 跳转到主界面并提示
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(LoginActivity.this, "用户名和密码蒙对了", Toast.LENGTH_SHORT).show();
            // 关闭当前活动
            finish();
        }else{
            Toast.makeText(LoginActivity.this,"这么简单，你都没有输入正确",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 获取布局控件，添加点击事件
     */
    private void bindViews() {
        etUser = (EditText) findViewById(R.id.et_user);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(this);
    }
}


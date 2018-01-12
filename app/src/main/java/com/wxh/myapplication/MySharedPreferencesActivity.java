package com.wxh.myapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Map;

/**
 *  SharedPreferences使用流程
 */
public class MySharedPreferencesActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLoginsp;
    private String strUser;
    private String strPwd;
    private SharedHelper sh;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_shared_preferences);
        context = getApplicationContext();
        sh = new SharedHelper(context);

        etUsername = (EditText) findViewById(R.id.et_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        btnLoginsp = (Button) findViewById(R.id.btn_loginsp);
        btnLoginsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strUser = etUsername.getText().toString();
                strPwd = etPassword.getText().toString();
                // 保存数据
                sh.save(strUser,strPwd);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 重启获取数据,显示到输入框里
        Map<String,String> data = sh.read();
        etUsername.setText(data.get("user"));
        etPassword.setText(data.get("pwd"));
    }
}

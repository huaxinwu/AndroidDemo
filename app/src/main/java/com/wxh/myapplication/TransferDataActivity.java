package com.wxh.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 *  Activity直接传递数据
 */
public class TransferDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_data);
        // ****************一次传递一个参数***********
        // 存数据
        Intent it1 = new Intent(TransferDataActivity.this,MyActivity.class);
        // value可以是任何数据类型，这里用的是字符串类型
        it1.putExtra("key","value");
        // 跳转到MyActivity界面
        startActivity(it1);
         // MyActivity类中可可以这样获取数据
        Intent it2 = getIntent();
        // 这里是字符串类型，还可以是其他类型getxxxExtra(key)方法
        String value = it2.getStringExtra("key");

        // ****************一次传递多个参数***********
        // 存数据
        Intent it3 = new Intent(TransferDataActivity.this,MyActivity.class);
        // 一批或批量
        Bundle bundle = new Bundle();
        bundle.putInt("num",1);
        bundle.putString("detail","呵呵");
        // 注意这里方法是复数，有S的
        it3.putExtras(bundle);
        startActivity(it3);

        // MyActivity类中可可以这样获取数据
        Intent it4 = getIntent();
        // 获取批量集合
        Bundle bundle1 = it4.getExtras();
        // 单个获取
        int n = bundle.getInt("num");
        String str = bundle.getString("detail");

    }

   
}

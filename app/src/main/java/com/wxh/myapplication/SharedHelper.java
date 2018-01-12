package com.wxh.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 *  共享数据工具类
 * Created by wxh on 2018/1/8.
 */
public class SharedHelper {

    private Context context;

    public SharedHelper(){

    }
    public SharedHelper(Context context){
        this.context = context;
    }

    /**
     *  保存用户名和密码
     * @param user
     * @param pwd
     */
    public void save(String user,String pwd){
        SharedPreferences sp = context.getSharedPreferences("mysp",Context.MODE_PRIVATE);
        // 获取边界器
        SharedPreferences.Editor editor = sp.edit();
        // 存入数据
        editor.putString("user",user);
        editor.putString("pwd",pwd);
        editor.commit();
        Toast.makeText(context,"数据已经写入SharedPreferences",Toast.LENGTH_SHORT).show();
    }

    /**
     *  读取一个sp文件
     * @return
     */
    public Map<String,String> read(){
        Map<String,String> map = new HashMap<>();
        SharedPreferences sp = context.getSharedPreferences("mysp", Context.MODE_PRIVATE);
        map.put("user",sp.getString("user",""));
        map.put("pwd",sp.getString("pwd",""));
        return map;
    }
}

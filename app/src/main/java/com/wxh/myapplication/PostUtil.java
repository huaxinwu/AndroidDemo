package com.wxh.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * @auther wxh
 * @date 2018/1/11 9:50
 */

public class PostUtil {

    /**
     * 登录地址
     */
    private static final String LGOGIN_URL = "http://192.168.1.52:28080/HttpTest/ServletForPost";

    private PostUtil() {

    }

    /**
     * 通过手机号和密码登录
     *
     * @param phone
     * @param pwd
     * @return String
     */
    public static String LoginByPost(String phone, String pwd) {
        String msg = "";
        try {
            URL url = new URL(LGOGIN_URL);
            // 打开一个连接
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置请求方式,请求超时信息
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.setConnectTimeout(5000);
            // 设置输入输出
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            // Post方式不能缓存,需手动设置为false
            httpURLConnection.setUseCaches(false);
            // 请求数据
            String data = "phone=" + URLEncoder.encode(phone, "UTF-8") + "&pwd=" + URLEncoder.encode(pwd, "UTF-8");
            // 还可以设置一些请求头信息
            // 获取输出流
            OutputStream out = httpURLConnection.getOutputStream();
            out.write(data.getBytes());
            // 刷新缓存，关闭流
            out.flush();
            out.close();
            // 如果请求成功
            if(httpURLConnection.getResponseCode() == 200){
                // 获取响应的输入流对象
                InputStream in = httpURLConnection.getInputStream();
                // 创建字节数组输出流对象
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                // 定义读取的长度
                int len = 0;
                // 定义缓冲区
                byte[] buffer = new byte[1024];
                // 按照缓冲区的大小，循环读取
                while((len = in.read(buffer))!= -1){
                    baos.write(buffer,0,len);
                }
                // 关闭流
                baos.close();
                in.close();
                // 返回字符串
                msg = new String(baos.toByteArray());
                return msg;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return msg;
    }


}

package com.wxh.myapplication;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  获取数据的类
 * @auther wxh
 * @date 2018/1/10 14:05
 */
public class GetData {

    private GetData(){

    }

    /**
     *  获取网络图片的数据
     * @param path
     * @return byte[]
     * @throws Exception 异常抛给调用者处理
     */
    public static byte[] getImage(String path) throws Exception{
        URL url = new URL(path);
        // 继承URLConnection
        // 打开一个连接
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        // 设置连接超时为5秒
        httpURLConnection.setConnectTimeout(5000);
        // 设置请求方法为GET方法
        httpURLConnection.setRequestMethod("GET");
        // 判断请求Url是否成功
        if(httpURLConnection.getResponseCode() != 200){
            throw  new RuntimeException("请求url失败");
        }
        // 获取输入流
        InputStream in = httpURLConnection.getInputStream();
        // 将流转换为字节数组
        byte[] buff = StreamUtil.read(in);
        // 关闭流
        in.close();
        return buff;
    }

    /**
     *  获取HTML源代码
     * @param path
     * @return String
     * @throws Exception 异常抛给调用者处理
     */
    public static String getHtml(String path) throws Exception{
        URL url = new URL(path);
        // 打开一个连接,这里用http，还有HTTPS
        HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
        // 设置连接超时时间、请求方法类型
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.setRequestMethod("GET");
        // 判断请求Url是否成功
        if(httpURLConnection.getResponseCode() != 200){
            throw  new RuntimeException("请求url失败");
        }
        // 获取输入流
        InputStream in = httpURLConnection.getInputStream();
        // 将流转换为字节数组
        byte[] buff = StreamUtil.read(in);
        // 关闭流
        in.close();
        // 将字节数组转换为字符串
        String htmlStr = new String(buff,"UTF-8");
        return htmlStr;
    }

}

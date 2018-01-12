package com.wxh.myapplication;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *  文件工具类
 * Created by wxh on 2018/1/8.
 */
public class FileHelper {

    private Context context;

    public FileHelper(){

    }
    public FileHelper(Context context){
        super();
        this.context = context;
    }

    /**
     *  将数据保存到一个文件里
     * @param fileName
     * @param fileContent
     */
    public void save(String fileName, String fileContent) throws Exception {
        // 文件操作模式使用私有模式
        // 打开文件输出流--字节流
        FileOutputStream out = context.openFileOutput(fileName, Context.MODE_PRIVATE);
        // 将String字符串以字节流的形式写入到输出流中
        out.write(fileContent.getBytes());
        // 关闭文件输出流
        out.close();

    }

    /**
     * 读取文件内容
     * @param fileName
     * @return String
     */
    public String read(String fileName) throws IOException {
        // 打开文件输入流--字节流
        FileInputStream in = context.openFileInput(fileName);
        // 使用字节数组提高读写速度
        byte[] buff = new byte[1024];
        StringBuilder sb = new StringBuilder("");
        int len = 0;
        // 读取文件内容
        while ((len = in.read(buff)) != -1) {
            sb.append(new String(buff, 0, len));
        }
        // 关闭文件输入流
        in.close();
        // 把读取文件内容返回
        return sb.toString();

    }

}

package com.wxh.myapplication;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *  流工具类
 * @auther wxh
 * @date 2018/1/10 11:31
 */
public class StreamUtil {

    private StreamUtil(){

    }

    /**
     *  将流转化为字节数组
     * @param in
     * @return byte[]
     * @throws Exception 异常抛出来，让调用者处理
     */
    public static byte[] read(InputStream in) throws Exception{
        // 通过输入流读取数据，输出流写入到字节数组中
        // 字节数组输出流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 初始化一个1024长度的字节数组，加快读取速度
        byte[] buff = new byte[1024];
        int len = 0;
        // 遍历，每次读取长度为1024
        while((len= in.read(buff))!=-1){
            // 每次写入长度为1024
            baos.write(buff,0,len);
        }
        // 关闭流
        in.close();
        // 转换为字节数组，结果返回
        return baos.toByteArray();
    }
}

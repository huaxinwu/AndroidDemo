package com.wxh.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

/**
 *  自定义活动
 * Created by wxh on 2018/1/3.
 */

public class MyActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity);

        /**
         * 系统给我们提供的常见的Activity
         */

        // 1.拨打电话
       // 给移动客服10086拨打电话
        Uri uriTel = Uri.parse("tel:10086");
        Intent intentTel = new Intent(Intent.ACTION_DIAL,uriTel);
        startActivity(intentTel);


        // 2.发送短信
        // 给10086发送内容为“Hello”的短信
        Uri uriSms = Uri.parse("smsto:10086");
        Intent intentSms = new Intent(Intent.ACTION_SENDTO,uriSms);
        // 传递参数
        intentSms.putExtra("sms_body","Hello");
        startActivity(intentSms);

        // 3.发送彩信（相当于发送带附件的短信）
        Intent intentMms = new Intent(Intent.ACTION_SEND);
        // 发送消息
        intentMms.putExtra("sms_body","Hello");
        Uri uriMms = Uri.parse("content://media/external/images/media/23");
        // 发送附件
        intentMms.putExtra(Intent.EXTRA_STREAM,uriMms);
        // 设置附件类型
        intentMms.setType("image/png");
        startActivity(intentMms);

        // 4.打开浏览器:
        // 打开Google主页
        Uri uriBrowser = Uri.parse("http://www.baidu.com");
        Intent intentBrowser = new Intent(Intent.ACTION_VIEW,uriBrowser);
        startActivity(intentBrowser);

        // 5.发送电子邮件:(阉割了Google服务的没戏!!!!)
        // 给someone@domain.com发邮件
        Uri uriEmail = Uri.parse("mailto:someone@domain.com");
        Intent intentEmail = new Intent(Intent.ACTION_SENDTO,uriEmail);
        startActivity(intentEmail);

        // 给someone@domain.com发邮件发送内容为“Hello”的邮件
        Intent intentContent = new Intent(Intent.ACTION_SEND);
        intentContent.putExtra(Intent.EXTRA_EMAIL,"someon@domain.com");
        intentContent.putExtra(Intent.EXTRA_SUBJECT,"Subject");
        intentContent.putExtra(Intent.EXTRA_TEXT,"Hello");
        // 设置邮件类型
        intentContent.setType("text/plain");
        startActivity(intentContent);

        // 给多人发邮件
        Intent intentManyPeople = new Intent(Intent.ACTION_SEND);
        // 收件人
        String[] tos = {"1@abc.com", "2@abc.com"};
        // 抄送
        String[] ccs = {"3@abc.com", "4@abc.com"};
        // 密送
        String[] bccs = {"5@abc.com", "6@abc.com"};
        intentManyPeople.putExtra(Intent.EXTRA_EMAIL, tos);
        intentManyPeople.putExtra(Intent.EXTRA_CC, ccs);
        intentManyPeople.putExtra(Intent.EXTRA_BCC, bccs);
        intentManyPeople.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        intentManyPeople.putExtra(Intent.EXTRA_TEXT, "Hello");
        intentManyPeople.setType("message/rfc822");
        startActivity(intentManyPeople);

        // 6.显示地图:
        // 打开Google地图中国北京位置（北纬39.9，东经116.3）
        Uri uriMap = Uri.parse("geo:39.9,116.3");
        Intent intentMap = new Intent(Intent.ACTION_VIEW, uriMap);
        startActivity(intentMap);

        // 7.路径规划
        // 路径规划：从北京某地（北纬39.9，东经116.3）到上海某地（北纬31.2，东经121.4）
        Uri uriPath = Uri.parse("http://maps.google.com/maps?f=d&saddr=39.9 116.3&daddr=31.2 121.4");
        Intent intentPath = new Intent(Intent.ACTION_VIEW, uriPath);
        startActivity(intentPath);

        // 8.多媒体播放:
        Intent intentMulmedia = new Intent(Intent.ACTION_VIEW);
        Uri uriMulmedia = Uri.parse("file:///sdcard/foo.mp3");
        intentMulmedia.setDataAndType(uriMulmedia, "audio/mp3");
        startActivity(intentMulmedia);

        // 获取SD卡下所有音频文件,然后播放第一首=-=
        Uri uriSD = Uri.withAppendedPath(MediaStore.Audio.Media.INTERNAL_CONTENT_URI, "1");
        Intent intentSD = new Intent(Intent.ACTION_VIEW, uriSD);
        startActivity(intentSD);










    }
}
